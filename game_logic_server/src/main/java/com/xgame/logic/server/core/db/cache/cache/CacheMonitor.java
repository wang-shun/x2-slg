/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 缓存观测器
 * @author jacky
 *
 */
public class CacheMonitor implements CacheMonitorMXBean{
	
	/**锁对象*/
	private final Lock lock = new ReentrantLock();
	
	/**实体类*/
	private Class<?> clazz;
	
	/**观测时间窗口(毫秒)*/
	private long windowSize = 10 * 60 * 1000L;
	
	/**当前窗口值*/
	private long window;
	
	/**总溢出*/
	private long totalEvicts;
	
	/**最大溢出*/
	private long maxEvicts;
	
	/**当前窗口溢出*/
	private long currEvicts;
	
	/**当前缓存实例数*/
	private long caches;
	
	/**历史最大缓存实例数*/
	private long maxCaches;
	
	/**最大实例数的时间*/
	private Date maxCachesTime = new Date();
	
	public CacheMonitor(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.window = System.currentTimeMillis() / windowSize;
		this.maxCachesTime = new Date();
	}
	
	/**
	 * 缓存一个对象
	 * @return >0 - 缓存新高值 <= 0 没有创新高
	 */
	public long cache(){
		lock.lock();
		try {
			caches++;
			if(caches > maxCaches){
				maxCaches = caches;
				maxCachesTime = new Date();
				return maxCaches;
			}
			return -1;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 溢出一个对象
	 * @return true-溢出新高
	 */
	public boolean evict(){
		lock.lock();
		try {
			caches--;
			this.totalEvicts++;
			long nowWindow = System.currentTimeMillis() / windowSize;
			if(window < nowWindow){
				window = nowWindow;
				currEvicts = 1;
			} else {
				currEvicts++;
			}
			if(currEvicts > maxEvicts){
				maxEvicts = currEvicts;
				return true;
			}
			return false;
		} finally {
			lock.unlock();
		}
	
	}


	@Override
	public String toString() {
		return "CacheMonitor [clazz=" + clazz + ", windowSize=" + windowSize
				+ ", window=" + window + ", totalEvicts=" + totalEvicts
				+ ", maxEvicts=" + maxEvicts + ", currEvicts=" + currEvicts
				+ ", caches=" + caches + ", maxCaches=" + maxCaches
				+ ", maxCachesTime=" + (maxCachesTime != null ? format(maxCachesTime) : 0) + "]";
	}

	@Override
	public String getClassName() {
		return this.clazz.getName();
	}

	@Override
	public long getTotalEvicts() {
		return this.totalEvicts;
	}

	@Override
	public long getMaxEvicts() {
		return this.maxEvicts;
	}

	@Override
	public long getCurrEvicts() {
		return this.currEvicts;
	}

	@Override
	public long getWindowSize() {
		return this.windowSize;
	}

	@Override
	public void setWindowSize(long windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public long getCaches() {
		return this.caches;
	}

	@Override
	public long getMaxCaches() {
		return this.maxCaches;
	}

	@Override
	public String getMaxCachesTime() {
		return format(this.maxCachesTime);
	}
	
	private String format(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

}
