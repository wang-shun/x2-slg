/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.management.ObjectName;

import com.xgame.logic.server.core.db.cache.EntityLifecycle;
import com.xgame.logic.server.core.db.cache.buffer.BufferMonitor.Transaction;
import com.xgame.logic.server.core.db.cache.cache.ICacheProxy;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.cache.exception.QueueTaskLimitedException;
import com.xgame.logic.server.core.db.cache.exception.QueueTaskShutdownException;
import com.xgame.logic.server.core.db.cache.exception.UpdateOnDeleteEntityExeption;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 基于队列的buffer实现
 * @author jacky
 *
 */
@Slf4j
public class QueuedExecutorBuffer<T extends IEntity<? extends Serializable>> implements Buffer<T> {

	private static final long serialVersionUID = -1474073674705951212L;
	
	
	public QueuedExecutorBuffer(Object identify, ICacheProxy<T> cacheProxy, int bufferSize, DumpExecutor dumpExecutor, RedisClient redisClient) {
		this.identify = identify;
		this.bufferSize = bufferSize;
		this.dumpExecutor = dumpExecutor;
		this.redisClient = redisClient;
		this.cacheProxy = cacheProxy;
		this.monitor = new BufferMonitor(identify);
		
		//注册moniter Mbean
		try {
			ManagementFactory.getPlatformMBeanServer().registerMBean(this.monitor, new ObjectName("dbcache.buffer.monitor:name=" + identify));
		} catch (Exception e) {
			log.error(String.format("注册 [%s] buffer moniter 异常!", identify), e);
		}
	}
	
	/** buffer的大小 */
	private int bufferSize;
	
	/**缓存代理*/
	private ICacheProxy<T> cacheProxy;
	
	/**相同的标识公用一个队列*/
	private Object identify;
	
	/**数据倾倒*/
	private DumpExecutor dumpExecutor;
	
	/**实体管理器*/
	private RedisClient redisClient;
	
	/**待更新id集合*/
	private final AtomicReference<Set<Object>> update = new AtomicReference<Set<Object>>(new ConcurrentHashSet<Object>());
	
	/**待删除id集合*/
	private final Set<Object> delete = new ConcurrentHashSet<Object>(); 
	
	/**监控对象*/
	private final BufferMonitor monitor;
	
	@Override
	public void save(final T entity) {
		
		//保存一个对象之前先移除掉删除状态
		delete.remove(entity.getId());
		
		//创建对象触发入库(大部分数据创建后只有更新操作,这里插入数据即时也是保证查询列表缓存正确的条件)
		this.submit(new DBAction(entity) {
			@Override
			public void beforeAction() {
				super.beforeAction();
				if(entity instanceof EntityLifecycle) {
					((EntityLifecycle) entity).onSave();
				}
			}
			
			@Override
			public void doAction() {
				String serialize = cacheProxy.serialize(entity);
				redisClient.hset(cacheProxy.getProxyClass().getSimpleName(), entity.getId(), serialize);	
			}
		});
	}

	@Override
	public void update(T entity) throws UpdateOnDeleteEntityExeption{
		
		//已经在删除的对象不能被更新
		if(delete.contains(entity.getId())) {
			throw new UpdateOnDeleteEntityExeption(entity);
		}
		
		final Set<Object> updateRef = update.get();
		updateRef.add(entity.getId());
		if(updateRef.size() >= bufferSize) {
			this.dump(updateRef);
		}
	}

	@Override
	public void updateImmediately(final T entity) throws UpdateOnDeleteEntityExeption {
		
		//已经在删除的对象不能被更新
		if(delete.contains(entity.getId())){
			throw new UpdateOnDeleteEntityExeption(entity);
		}
		
		this.submit(new DBAction(entity) {
			
			@Override
			public void beforeAction() {
				super.beforeAction();
				if(entity instanceof EntityLifecycle){
					((EntityLifecycle) entity).onUpdate();
				}
			}
			
			@Override
			public void doAction() {
				String serialize = cacheProxy.serialize(entity);
				redisClient.hset(cacheProxy.getProxyClass().getSimpleName(), entity.getId(), serialize);
			}
		});
	}
	
	
	@Override
	public void delete(final T entity) {
		final Serializable id = entity.getId();
		
		delete.add(id);
		update.get().remove(id);//从待保存的缓冲中删除
		
		this.submit(new DBAction(entity) {
			
			@Override
			public void beforeAction() {
				super.beforeAction();
				if(entity instanceof EntityLifecycle){
					((EntityLifecycle) entity).onDelete();
				}
			}
			
			@Override
			public void doAction() {
				try {
					redisClient.hdel(cacheProxy.getProxyClass(), entity.getId());
				} catch(Exception e) {
					log.error("删除key出现错误.", e);
				}
			}

			@Override
			public void afterAction() {
				delete.remove(id);//数据库删除后移除掉删除对象
				super.afterAction();
			}
		});
	}
	

	/**
	 * 比较期望的引用后再dump(如果期望的与当前的一样则执行dump反则就放弃)
	 * @param expectRef
	 */
	public int dump(Set<Object> expectRef){
		
		Set<Object> current = null;
		
		//这里实际碰撞的几率很小内部操作只是个引用的切换
		synchronized (this) {
			if(expectRef != null){
				if(expectRef != update.get()){//引用不一样放弃dump
					return 0;
				}
				
				current = update.getAndSet(new ConcurrentHashSet<Object>());//切换一个新的更新集合装新的更新
				if(expectRef != current){//引用不一样放弃dump
					return 0;
				}
			} else {
				current = update.getAndSet(new ConcurrentHashSet<Object>());//为null强制执行
			}
		}
		
		int dump = 0;
		
		if(current != null && !current.isEmpty()) {
			List<T> dumpMap = new ArrayList<T>();
			Iterator<Object> it = current.iterator();
			while(it.hasNext()) {
				final Object id = it.next();
				try {
					final T entity = (T) this.cacheProxy.get((Serializable)id);
					if(entity != null) {
						new DBAction(entity) {
							@Override
							public void beforeAction() {
								super.beforeAction();
								if(entity instanceof EntityLifecycle) {
									((EntityLifecycle) entity).onUpdate();
								}
							}
							
							@Override
							public void doAction() {
								try {
									dumpMap.add(entity);
								} catch (Exception e) {
									log.error("update action 处理线程入库序列化异常:", e);
								}
							}

							@Override
							public void afterAction() {
								super.afterAction();
							}
						}.run();
						dump++;
					} else {
						log.error(String.format("dbcache buffer dump [%s] id:{%s} 对象发现在缓存中不存在!", this.identify, id));
					}
				} catch (Exception e) {
					log.error(String.format("dbcache buffer dump [%s] 异常!", this.identify), e);
				} finally {
					it.remove();
				}
			}
			
			try {
				Jedis jedis = redisClient.getDbClient();
				Pipeline pipeline = jedis.pipelined();
				try {
					for (T entity : dumpMap) {
						pipeline.hset(cacheProxy.getProxyClass().getSimpleName(), String.valueOf(entity.getId()), cacheProxy.serialize(entity));
					}
					pipeline.sync();
				} catch (Exception e) {
					log.error("数据库查询异常", e);
					redisClient.returnBroken(jedis);
				} finally {
					redisClient.release(jedis);
				}
				
				log.info(String.format("入库实体[%s], 入库缓存数量:[%s]", cacheProxy.getProxyClass().getSimpleName(), dump));
			} catch (Exception e) {
				log.error("入库缓存异常:",e);
			}
		}
		return dump;
	}
	
	
	@Override
	public int dump() {
		return this.dump(null);//强制dump
	}
	
	/**
	 * 提交数据库操作
	 */
	private void submit(Runnable runnable){
		try {
			dumpExecutor.execute(identify, runnable);
		} catch (QueueTaskLimitedException e) {
			log.error("dbcache dump线程池 [{}] 队列长度限制!");
		} catch (QueueTaskShutdownException e) {
			log.error("dbcache dump线程池 [{}] 已被关闭!");
		}
	}
	
	
	
	//一个数据库动作
	private abstract class DBAction implements Runnable{
		
		/**待操作的实体*/
		private IEntity<?> entity;
		
		/**事务记录对象*/
		private Transaction transaction;
		
		public DBAction(IEntity<?> entity) {
			super();
			this.entity = entity;
			if(monitor.isOpen()){
				transaction = monitor.beginTransaction();
			}
		}

		//执行数据库动作
		public abstract void doAction();
		
		//执行action之后需要做的事
		public void afterAction(){
			if(transaction != null){
				transaction.end();
				//超越了
				if(monitor.endTransaction(transaction)){
					log.info("buffer_exceed: " + monitor.toString());
				}
			}
		}
		
		//执行action之前需要做的事
		public void beforeAction(){
			if(transaction != null){
				transaction.begin();
			}
		}
		
		@Override
		public void run(){
			
			//前置处理
			try {
				this.beforeAction();
			} catch (Exception e) {
				log.error(String.format("实体类 [%s] id : [%s}] 数据库操作前处理型异常!", this.entity.getClass().getName(), this.entity.getId()), e);
			}
			
			//入库处理
			try {
				this.doAction();
			} catch (Exception e) {
				log.error(String.format("实体类 [%s] id : [%s] 数据库操作异常!", this.entity.getClass().getName(), this.entity.getId()), e);
			} 
			
			//后置处理
			try {
				this.afterAction();
			} catch (Exception e) {
				log.error(String.format("实体类 [%s] id : [%s] 数据库操作后处理行为异常!", this.entity.getClass().getName(), this.entity.getId()), e);
			}
		}
		
	}



	@Override
	public BufferStatus guess(Serializable id) {
		if(delete.contains(id)){
			return BufferStatus.WAITING_DELETE;
		} else if(update.get().contains(id)){
			return BufferStatus.WAITING_UPDATE;
		}
		return BufferStatus.TRANSIENT;
	}

}
