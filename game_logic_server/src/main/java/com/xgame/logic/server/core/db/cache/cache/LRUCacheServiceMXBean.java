/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import javax.management.MXBean;

/**
 * 缓存器
 * @author jakcy
 *
 */
@MXBean
public interface LRUCacheServiceMXBean {

	/**
	 * 返回当前缓存大小
	 * @return
	 */
	int getCacheSize();
	
	/**
	 * 获取配置的最大缓存大小
	 * @return
	 */
	int getMaxCacheSize();
	
	/**
	 * 获取实例最多的类信息
	 * @return
	 */
	String getTopCaches();
}
