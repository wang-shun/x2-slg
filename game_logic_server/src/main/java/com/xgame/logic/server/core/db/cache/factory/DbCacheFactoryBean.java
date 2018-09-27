/**
 * 
 */
package com.xgame.logic.server.core.db.cache.factory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xgame.logic.server.core.db.cache.DbCacheServiceImpl;
import com.xgame.logic.server.core.db.cache.buffer.Buffer;
import com.xgame.logic.server.core.db.cache.buffer.DumpExecutor;
import com.xgame.logic.server.core.db.cache.buffer.DumpWorker;
import com.xgame.logic.server.core.db.cache.buffer.QueuedExecutorBuffer;
import com.xgame.logic.server.core.db.cache.cache.ICacheProxy;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.cache.executor.ExecutorConfig;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.utils.ClassUtil;

/**
 * dbCache工厂
 * @author jacky
 *
 */
@Slf4j
public class DbCacheFactoryBean implements ApplicationContextAware{
	
	//**********************************需要配置的属性*****************************************
	/**dump线程池核心线程数*/
	private int dumpCoreThreadSize = 12;
	/**dump线程池最大线程数*/
	private int dumpMaxThreadSize = 20;
	/**实体缓存缓存大小*/
	private int entityCacheSize = 600000;
	/**查询缓存大小*/
	private int queryCacheSize = 500000;
//	/**持久化器*/
//	private Persister persister;
	/**倾倒时间间隔(毫秒) >0 开启定时保存否则不开启*/
	private long dumpIntervals;
	
	private String packageName = "com.xgame";
	
	//**********************************需要配置的属性*****************************************
	
	public int getDumpCoreThreadSize() {
		return dumpCoreThreadSize;
	}
	public void setDumpCoreThreadSize(int dumpCoreThreadSize) {
		this.dumpCoreThreadSize = dumpCoreThreadSize;
	}
	public long getDumpIntervals() {
		return dumpIntervals;
	}
	public void setDumpIntervals(long dumpIntervals) {
		this.dumpIntervals = dumpIntervals;
	}
	public int getEntityCacheSize() {
		return entityCacheSize;
	}
	public void setEntityCacheSize(int entityCacheSize) {
		this.entityCacheSize = entityCacheSize;
	}
	public int getQueryCacheSize() {
		return queryCacheSize;
	}
	public void setQueryCacheSize(int queryCacheSize) {
		this.queryCacheSize = queryCacheSize;
	}
	public int getDumpMaxThreadSize() {
		return dumpMaxThreadSize;
	}
	public void setDumpMaxThreadSize(int dumpMaxThreadSize) {
		this.dumpMaxThreadSize = dumpMaxThreadSize;
	}
	
	/**buffer*/
	@SuppressWarnings("rawtypes")
	private Map<Class, Buffer> buffers;
	
	private Map<Class<?>, ICacheProxy<IEntity<?>>> cacheProxyMap;
	
	/**dump线程池*/
	private DumpExecutor dumpExecutor;
	
	/**dump工作者线程*/
	private DumpWorker dumpWorker;
	
	public void init() {
		// 加载class
		this.cacheProxyMap = initializeCacheService();
		this.dumpExecutor = this.initializeDumpExecutor(dumpCoreThreadSize, dumpMaxThreadSize);
		this.buffers = this.initializeBuffers();

		//大于才开启
		if(dumpIntervals > 0) {
			dumpWorker = new DumpWorker(dumpIntervals, buffers);
			dumpWorker.start();
			log.info("启动dbcache定时入库线程时间间隔为 [{}] 毫秒...", dumpIntervals);
		} 
			
		DbCacheServiceImpl dbCacheService = applicationContext.getBean(DbCacheServiceImpl.class);
		dbCacheService.init(dumpExecutor, dumpWorker, buffers, cacheProxyMap);
//		this.registerMeter(queryCache);
	}
	
	//初始化缓存器
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<Class<?>, ICacheProxy<IEntity<?>>> initializeCacheService() {
		Map<Class<?>, ICacheProxy<IEntity<?>>> currentMap = new ConcurrentHashMap<Class<?>, ICacheProxy<IEntity<?>>>();
		Map<String, ICacheProxy> cacheMap = applicationContext.getBeansOfType(ICacheProxy.class);
		if(cacheMap != null) {
			for(ICacheProxy proxy : cacheMap.values()) {
				currentMap.put(proxy.getProxyClass(), proxy);
			}
		}
		return currentMap;
	}
	
	//初始化dump线程池
	private DumpExecutor initializeDumpExecutor(int coreThreadSize, int maxThreadSize) {
		ExecutorConfig executorConfig = new ExecutorConfig("dbcache入库线程池");
		executorConfig.setCoreThreadSize(coreThreadSize);
		executorConfig.setMaxThreadSize(maxThreadSize);
		executorConfig.setKeepAliveMillis(60000);//一分钟
		
		//注册到容器
		RuntimeBeanDefinitionBuilder beanDefinitionBuilder = new RuntimeBeanDefinitionBuilder(DumpExecutor.class).addConstructorArgValue(executorConfig);
		DumpExecutor executor = (DumpExecutor)beanDefinitionBuilder.register(applicationContext);
		return executor;
	}
	
	//初始化buffers
	@SuppressWarnings({"rawtypes"})
	private Map<Class, Buffer> initializeBuffers() {
		Collection<Class<IEntity>> entityClasses = ClassUtil.getSubClasses(packageName, IEntity.class).values();
		Map<Class, Buffer> buffers = new ConcurrentHashMap<Class, Buffer>();
		if(entityClasses != null){
			for(Class clazz : entityClasses) {
				String identify = clazz.getName();
				int bufferSize = 2000; //默认600
				ICacheProxy<IEntity<?>> cacheProxy = cacheProxyMap.get(clazz);
				if(cacheProxy == null) {
					cacheProxy = getSuperClassProxy(clazz);
					if(cacheProxy != null) {
						cacheProxyMap.put(clazz, cacheProxy);
					}
				}
				
				RedisClient redisClient = applicationContext.getBean(RedisClient.class);
				Buffer<?> buffer = new QueuedExecutorBuffer<>(identify, cacheProxy, bufferSize, dumpExecutor, redisClient);
				buffers.put(clazz, buffer);
			};
		}
		return buffers;
	}
	
	public ICacheProxy<IEntity<?>> getSuperClassProxy(Class<?> clazz) {
		Set<Class<?>> set = cacheProxyMap.keySet();
		if(set != null && !set.isEmpty()) {
			for(Class<?> simClass : set) {
				if(simClass.isAssignableFrom(clazz) && !simClass.equals(clazz)) {
					return cacheProxyMap.get(simClass);
				}
			}
		}
		return null;
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
