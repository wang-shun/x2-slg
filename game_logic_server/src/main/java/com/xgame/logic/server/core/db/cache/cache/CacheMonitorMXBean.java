/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import javax.management.MXBean;

/**
 * 缓存观测器Mbean接口
 * @author jiangxt
 *
 */
@MXBean
public interface CacheMonitorMXBean {
	
	/**
	 * 实体类名
	 * @return
	 */
	String getClassName();
	
	/**
	 * 累计总溢出数
	 * @return
	 */
	long getTotalEvicts();
	
	/**
	 * 最大溢出数
	 * @return
	 */
	long getMaxEvicts();
	
	/**
	 * 当前溢出数
	 * @return
	 */
	long getCurrEvicts();
	
	/**
	 * 时间窗口大小(毫秒)
	 * @return
	 */
	long getWindowSize();
	
	/**
	 * 设置时间窗口大小(毫秒)
	 * @param windowSize
	 */
	void setWindowSize(long windowSize);
	
	/**
	 * 当前缓存数
	 * @return
	 */
	long getCaches();
	
	/**
	 * 历史最大缓存数
	 * @return
	 */
	long getMaxCaches();
	
	/**
	 * 历史最大缓存数时间
	 * @return
	 */
	String getMaxCachesTime();

}
