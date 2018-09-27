/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;




/**
 * 缓存服务接口
 * @author jacky
 *
 */
public interface CacheService {
	
	/**
	 * null对象
	 */
	Object NULL_SIGNAL = new Object();
	
	/**
	 * 根据主键获取缓存对象
	 * @param id
	 * @return
	 */
	CachedObject get(CacheKey cacheKey);
	
	/**
	 * 放入一个缓存对象如果之前存在则不放入
	 * @param id
	 * @param entity
	 * @return 再次操作之前存在于缓存中的实体对象
	 */
	CachedObject putIfAbsent(CacheKey cacheKey, CachedObject cachedObject);
	
	/**
	 * 强制放入一个缓存对象如果之前存在则替换掉
	 * @param id
	 * @param entity
	 * @return 再次操作之前存在于缓存中的实体对象
	 */
	CachedObject put(CacheKey cacheKey, CachedObject cachedObject);
	
	/**
	 * 判断缓存中是否存在指定的key
	 * @param cacheKey
	 * @return
	 */
	boolean contains(CacheKey cacheKey);
	
	/**
	 * 获取当前已占用的缓存大小
	 * @return
	 */
	int getCacheSize();
	
	/**
	 * 为缓存加入溢出监听器
	 * @param evictionListener
	 */
	void listener(EvictionListener evictionListener);
	
}
