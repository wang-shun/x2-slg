package com.xgame.framework.objectpool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ObjectPool<T> {

	private GenericObjectPool<T> objectPool;

 
	public ObjectPool(Class<T> handleClass, ObjectPoolProperties properties) {

		// 初始化对象池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setBlockWhenExhausted(properties.blockWhenExhausted);
		poolConfig.setMaxWaitMillis(properties.maxWait);
		poolConfig.setMinIdle(properties.minIdle);
		poolConfig.setMaxIdle(properties.maxIdle);
		poolConfig.setMaxTotal(properties.maxTotal);
		poolConfig.setTestOnBorrow(properties.testOnBorrow);
		poolConfig.setTestOnReturn(properties.testOnReturn);
		poolConfig.setTestOnCreate(properties.testOnCreate);
		poolConfig.setTestWhileIdle(properties.testWhileIdle);
		poolConfig.setLifo(properties.lifo);
		// 初始化对象池
		objectPool = new GenericObjectPool<T>(new ObjectFactory(handleClass));

	}

	public T borrowObject() throws Exception {
		return (T)objectPool.borrowObject();
	}

	public void returnObject(IPooledObject obj) {
		objectPool.returnObject((T) obj);
	}
}
