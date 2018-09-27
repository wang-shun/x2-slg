/**
 * 
 */
package com.xgame.logic.server.core.db.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.cache.buffer.Buffer;
import com.xgame.logic.server.core.db.cache.buffer.BufferStatus;
import com.xgame.logic.server.core.db.cache.buffer.DumpExecutor;
import com.xgame.logic.server.core.db.cache.buffer.DumpWorker;
import com.xgame.logic.server.core.db.cache.cache.ICacheProxy;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.cache.exception.PrimaryKeyNullException;
import com.xgame.logic.server.core.db.cache.exception.ServiceStartedException;
import com.xgame.logic.server.core.utils.InjectorUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于缓冲池倾倒入库机制的缓存器
 * @author jiangxt
 *
 */
@Slf4j
@Component
public class DbCacheServiceImpl implements DbCacheService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**实体类的buffer*/
	@SuppressWarnings("rawtypes")
	private Map<Class, Buffer> buffers;
	
	/**dump线程池*/
	private DumpExecutor dumpExecutor;
	
	/**dump工作者线程*/
	private DumpWorker dumpWorker; 
	
	private Map<Class<?>, ICacheProxy<IEntity<?>>> cacheProxyMap;
	
	public void init(DumpExecutor dumpExecutor, DumpWorker dumpWorker, @SuppressWarnings("rawtypes") Map<Class, Buffer> buffers, Map<Class<?>, ICacheProxy<IEntity<?>>> cacheProxyMap) {
		this.dumpExecutor = dumpExecutor;
		this.dumpWorker = dumpWorker;
		this.buffers = buffers;
		this.cacheProxyMap = cacheProxyMap;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Buffer<T> getBuffer(Class<T> entityClass){
		return buffers.get(entityClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IEntity<? extends Serializable>> T get(Class<T> clazz, Serializable id) {
		
		Buffer<T> buffer = this.getBuffer(clazz);
		
		if(id == null) {
			return null;
		}
		
		if(buffer.guess(id) == BufferStatus.WAITING_DELETE){//当前正在删除中
			return null;
		}
		
		ICacheProxy<IEntity<?>> cacheProxy = cacheProxyMap.get(clazz);
		if(cacheProxy == null) {
			logger.error(String.format("类[%s],没有找到对应缓存。", clazz));
			return null;
		}
		
		T t = (T) cacheProxy.get(id);
		
		if (t == null) {
			final Lock lock = this.getLock(clazz, id);
			lock.lock();
			try {
				t = (T) cacheProxy.get(id);
				if (t == null) {
					// 从数据库当中查找
					String value = InjectorUtil.getInjector().redisClient.hget(cacheProxy.getProxyClass().getSimpleName(), String.valueOf(id));
					if(!StringUtils.isEmpty(value)) {
						t = (T) cacheProxy.deserialize(value);
					}
					
					if(t == null) {//数据库为空
						return null;
					} else {
						//回调接口
						if(t instanceof EntityLifecycle) {
							try {
								((EntityLifecycle) t).onLoad();
							} catch (Exception e) {
								logger.error(String.format("实体 class : [%s] id :[%s] 在执行 onLoad 时异常 ", clazz.getName(), id), e);
							}
						}
					}
					cacheProxy.add(t);
					return t;
				}
			} finally {
				lock.unlock();
				this.removeLock(clazz, id);
			}
		}
		return t;
	}
	
	//临时对象锁
	private final ConcurrentMap<Class<?>, ConcurrentMap<Serializable, Lock>> temporaryLocks = new ConcurrentHashMap<Class<?>, ConcurrentMap<Serializable, Lock>>();
	
	private Lock getLock(Class<?> clazz, Serializable id) {
		ConcurrentMap<Serializable, Lock> classLock = temporaryLocks.get(clazz);
		if(classLock == null){
			classLock = new ConcurrentHashMap<Serializable, Lock>();
			temporaryLocks.putIfAbsent(clazz, classLock);
			classLock = temporaryLocks.get(clazz);
		}
		
		Lock lock = classLock.get(id);
		if(lock == null){
			lock = new ReentrantLock();
			classLock.putIfAbsent(id, lock);
			lock = classLock.get(id);
		}
		
		return lock;
		
	}
	
	private void removeLock(Class<?> clazz, Serializable id){
		ConcurrentMap<Serializable, Lock> classLock = temporaryLocks.get(clazz);
		if(classLock != null){
			classLock.remove(id);
		}
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends IEntity> T create(T entity) {
		
		if(entity == null) {
			return null;
		}
		
		Class clazz = entity.getClass();
		
		Serializable id = entity.getId();
		if(entity.getId() == null) {
			logger.error("实体类 [{}] 持久化时主键为空!", clazz.getName());
			throw new PrimaryKeyNullException(clazz);
		}
		
		ICacheProxy<IEntity<?>> cacheProxy = (ICacheProxy<IEntity<?>>)cacheProxyMap.get(clazz);
		if(cacheProxy == null) {
			log.error("缓存代理对象不存在,class:[%s]", clazz.toString());
		}
		
		T entityInCache = (T)cacheProxy.get(entity.getId());
		
		//缓存中已经存在
		if(entityInCache != null) {
			return entityInCache;
		}
		
		Lock lock = this.getLock(clazz, id);
		lock.lock();
		try {
			entityInCache = (T)cacheProxy.get(entity.getId());
			
			//缓存中已经存在
			if(entityInCache != null) {
				return entityInCache;
			} else {
				cacheProxy.add(entity);
			}
			
			Buffer<T> buffer = this.buffers.get(clazz);
			buffer.save(entity);

		} finally {
			lock.unlock();
			this.removeLock(clazz, id);
		}
		
		return (T)cacheProxy.get(entity.getId());
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends IEntity<? extends Serializable>> void update(T entity) {
		
		if(entity == null) {
			return;
		}
		
		Class clazz = entity.getClass();
		
		if(entity.getId() == null) {
			throw new PrimaryKeyNullException(clazz);
		}
		
		ICacheProxy<T> cacheProxy = (ICacheProxy<T>)cacheProxyMap.get(clazz);
		if(cacheProxy == null) {
			log.error("缓存代理对象不存在, class:[%s]", clazz.toString());
			return;
		}
		
		//如果缓存中不存在就放入缓存中
		if(!cacheProxy.exist(entity)) {
			logger.info(String.format("%s 更新时缓存不存在替换缓存数据...", entity.getId()));
			cacheProxy.add(entity);
		} else {
			cacheProxy.update(entity);
		}
		
		Buffer<T> buffer = this.getBuffer(clazz);
		try {
			buffer.update(entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T extends IEntity<? extends Serializable>> void delete(T entity) {
		if(entity == null){
			return;
		}
		
		Class clazz = entity.getClass();
		
		if(entity.getId() == null) {
			throw new PrimaryKeyNullException(clazz);
		}
		
		ICacheProxy<T> cacheProxy = (ICacheProxy<T>)cacheProxyMap.get(clazz);
		if(cacheProxy == null) {
			log.error("缓存代理对象不存在,class:[%s]", clazz.toString());
			return;
		}
		cacheProxy.remove(entity);
		
		Buffer<T> buffer = this.getBuffer(clazz);
		buffer.delete(entity);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void dumpBuffer() {
		for(Entry<Class, Buffer> entry: buffers.entrySet()) {
			Class<?> clazz = entry.getKey();
			Buffer<?> buffer = entry.getValue();
			try {
				buffer.dump();
			} catch (Exception e) {
				logger.error(String.format("倾倒类 [%s] buffer 入库异常!", clazz.getSimpleName()),  e);
			}
		}
	}

	/**标记是否已经开始工作*/
	private final AtomicBoolean start = new AtomicBoolean(false);
	
	@Startup(order = StartupOrder.DBCACHE_START, desc="dbcache启动加载")
	public void start() {
		if(!start.compareAndSet(false, true)) {
			throw new ServiceStartedException(this);
		}
	}

	@SuppressWarnings("rawtypes")
	@Shutdown(order = ShutdownOrder.DBCACHE, desc = "缓存管理器")
	public void stop() {
		this.start.set(false);
		//将buffer中的数据入库
		if(buffers != null && !buffers.isEmpty()) {
			for(Entry<Class, Buffer> entry : buffers.entrySet()) {
				Class clazz = entry.getKey();
				Buffer buffer = entry.getValue();
				try {
					buffer.dump();
				} catch(Exception e) {
					logger.error(String.format("倾倒 [%s] 类异常!", clazz), e);
				}
			}
		}
		
		try {
			if (dumpWorker != null) {
				dumpWorker.interrupt();
			}
		} catch (Exception e) {
			logger.error("中断dump工作者线程异常!", e);
		}
		
		if (dumpExecutor != null) {
			dumpExecutor.shutdown(false);
		}
		
		logger.info("关闭dbcache...");
	}

}
