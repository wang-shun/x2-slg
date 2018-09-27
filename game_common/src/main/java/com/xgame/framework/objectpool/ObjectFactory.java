package com.xgame.framework.objectpool;

import org.apache.commons.pool2.BasePooledObjectFactory;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ObjectFactory<T> extends BasePooledObjectFactory<IPooledObject> {

	private Class<T> handleClass;

	public ObjectFactory(Class<T> handleClass) {
		this.handleClass = handleClass;
	}

	/**
	 * 创建对象
	 */
	@Override
	public IPooledObject create() throws Exception {
		return (IPooledObject) handleClass.newInstance();
	}

	/**
	 * 用PooledObject封装对象放入池中
	 */
	@Override
	public PooledObject<IPooledObject> wrap(IPooledObject obj) {
		return new DefaultPooledObject<IPooledObject>(obj);
	}

	/**
	 * 销毁对象
	 */
	@Override
	public void destroyObject(PooledObject<IPooledObject> p) throws Exception {
		IPooledObject obj = p.getObject();
		obj.poolClear();
		super.destroyObject(p);
	}

	/**
	 * 验证对象
	 */
	@Override
	public boolean validateObject(PooledObject<IPooledObject> p) {
		IPooledObject t = p.getObject();
		return t.poolValidate();
	}
}
