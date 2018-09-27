/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap.Builder;
import com.xgame.logic.server.core.db.cache.exception.MultiEvictionListenerException;

/**
 * 基于LRU策略的缓存服务
 * @author jacky
 *
 */
public class LRUCacheService implements CacheService, LRUCacheServiceMXBean {
	
	private static final Logger logger = LoggerFactory.getLogger(LRUCacheService.class);
	
	private final ConcurrentLinkedHashMap<CacheKey, CachedObject> LRU_MAP;
	
	@SuppressWarnings("rawtypes")
	private final ConcurrentMap<Class, CacheMonitor> monitors = new ConcurrentHashMap<>();
	
	/** 溢出监听器*/
	private EvictionListener evictionListener;
	
	/**最大缓存大小*/
	private int maxCacheSize = 600000;
	
	@SuppressWarnings("rawtypes")
	public LRUCacheService(Map<Class, CacheMonitor> monitors, int maxCacheSize, EvictionListener evictionListener){
		
		this.evictionListener = evictionListener;
		this.maxCacheSize = maxCacheSize;
		
		if(monitors != null){
			this.monitors.putAll(monitors);
		}
		
		Builder<CacheKey, CachedObject> builder = new ConcurrentLinkedHashMap.Builder<CacheKey, CachedObject>().maximumWeightedCapacity(maxCacheSize);

		builder.listener(new com.googlecode.concurrentlinkedhashmap.EvictionListener<CacheKey, CachedObject>() {
			
			@Override
			public void onEviction(CacheKey key, CachedObject value) {
				final EvictionListener evictionListener = LRUCacheService.this.evictionListener;
				if(evictionListener != null){
					evictionListener.onEviction(key, value);
				}
				
				CacheMonitor monitor = getMonitor(key);
				if(monitor.evict() || monitor.getTotalEvicts() % 5000 == 0){//每次创每秒移除频率新高或者总溢出数增长1000 就输出
					logger.info("evict_exceed:" + monitor.toString());
				}
			}
		});
		
		this.LRU_MAP = builder.build();
	}
	
	private CacheMonitor getMonitor(CacheKey key){
		@SuppressWarnings("rawtypes")
		Class clazz = key.getEntityClass();
		CacheMonitor monitor = monitors.get(clazz);
		if(monitor == null){
			monitor = new CacheMonitor(clazz);
			monitors.putIfAbsent(clazz, monitor);
			monitor = monitors.get(clazz);
		}
		return monitor;
	}
	
	@Override
	public CachedObject get(CacheKey cacheKey) {
		return LRU_MAP.get(cacheKey);
	}

	@Override
	public CachedObject putIfAbsent(CacheKey cacheKey, CachedObject cachedObject) {
		CachedObject prev = LRU_MAP.putIfAbsent(cacheKey, cachedObject);
		if(prev == null){
			CacheMonitor monitor = getMonitor(cacheKey);
			long topCacheSize = monitor.cache();
			if(topCacheSize > 0 && topCacheSize % 1000 == 0){//当前缓存数大于500才log 否则开服初期会刷日志
				logger.info("cache_exceed :" + monitor.toString());
			}
		}
		return prev;
	}

	@Override
	public CachedObject put(CacheKey cacheKey, CachedObject cachedObject) {
		CachedObject prev = LRU_MAP.put(cacheKey, cachedObject);
		if(prev == null){
			CacheMonitor monitor = getMonitor(cacheKey);
			long topCacheSize = monitor.cache();
			if(topCacheSize > 0 && topCacheSize % 1000 == 0){//当前缓存数大于500才log 否则开服初期会刷日志
				logger.info("cache_exceed:" + monitor.toString());
			}
		}
		return prev;
	}

	@Override
	public int getCacheSize() {
		return LRU_MAP.size();
	}


	@Override
	public void listener(EvictionListener evictionListener) {
		if(this.evictionListener != null){
			throw new MultiEvictionListenerException();
		}
		this.evictionListener = evictionListener;
	}

	@Override
	public int getMaxCacheSize() {
		return this.maxCacheSize;
	}

	@Override
	public String getTopCaches() {
		List<CacheMonitor> monitors = new ArrayList<>();
		monitors.addAll(this.monitors.values());
		
		Collections.sort(monitors, new Comparator<CacheMonitor>() {

			@Override
			public int compare(CacheMonitor o1, CacheMonitor o2) {
				long n1 = o1.getCaches();
				long n2 = o2.getCaches();
				if(n1 == n2){
					return 0;
				} else {
					return n1 > n2 ? -1 : 1;
				}
			}
		});
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < 5 && i < monitors.size(); i++){
			CacheMonitor monitor = monitors.get(i);
			String message = String.format("{类名:%s,当前缓存实例数:%s,历史最大缓存实例数:%s}", monitor.getClassName(), monitor.getCaches(), monitor.getMaxCaches());
			builder.append(message).append(",");
		}
		
		if(builder.length() > 0){
			builder.deleteCharAt(builder.length() - 1);
		}
		
		return builder.toString();
	}

	@Override
	public boolean contains(CacheKey cacheKey) {
		return LRU_MAP.containsKey(cacheKey);
	}
	
}
