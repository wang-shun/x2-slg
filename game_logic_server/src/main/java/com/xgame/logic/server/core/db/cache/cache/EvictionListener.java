/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;


/**
 * 缓存溢出监听器
 * @author jiangxt
 *
 */
public interface EvictionListener {

	/**
	 * 当指定的键值被溢出缓存是调用
	 * @param key
	 * @param value
	 */
	void onEviction(CacheKey key, CachedObject value);
	
}
