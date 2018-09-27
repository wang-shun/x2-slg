package com.xgame.logic.server.core.db.cache.cache;

import java.io.Serializable;

import com.xgame.logic.server.core.db.cache.entity.IEntity;


/**
 * 缓存代理类
 * @author jacky.jiang
 *
 */
public interface ICacheProxy<T extends IEntity<?>> {
	
	/**
	 * 向缓存当中添加
	 * @param t
	 */
	void add(T t);
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	
	/**
	 * 更新缓存
	 * @param t
	 */
	void update(T t);

	/**
	 * 移除缓存当中的元素
	 * @param t
	 */
	void remove(T t);
	
	/***
	 * 清空缓存
	 */
	void clean();
	
	/**
	 * 判断是否存在当前对象
	 * @param t
	 * @return
	 */
	boolean exist(T t);
	
	/**
	 * 反序列化
	 * @param byteBuffer
	 * @return
	 */
	T deserialize(String value);
	
	/**
	 * 序列化保存对象
	 * @param entity
	 * @return
	 */
	String serialize(T entity);
	
	/**
	 * 获取类class
	 * @return
	 */
	Class<?> getProxyClass();
	
}
