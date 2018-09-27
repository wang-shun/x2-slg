package com.xgame.logic.server.core.db.cache.cache;

import java.io.Serializable;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.utils.InjectorUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LRUCacheProxy<T extends IEntity<?>> extends CacheProxy<T> {
	
	/**
	 * 缓存
	 */
	private ConcurrentLinkedHashMap<Serializable, T> LRUCache = null;
	
	public void init(int initCacheSize) {
		// 在线玩家缓存
		com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap.Builder<Serializable, T> builder = new ConcurrentLinkedHashMap.Builder<Serializable, T>().maximumWeightedCapacity(initCacheSize);
		builder.listener(new com.googlecode.concurrentlinkedhashmap.EvictionListener<Serializable, T>() {
			@Override
			public void onEviction(Serializable key, T data) {
				String serialize = serialize(data);
				InjectorUtil.getInjector().redisClient.hset(getProxyClass().getSimpleName(), data.getId(), serialize);
				log.error("移除缓存当中的key:"+key+",value:"+data.toString());
			}
		});
		this.LRUCache = builder.build();
	}
	
	@Override
	public void add(T t) {
		LRUCache.put(t.getId(), t);
	}

	@Override
	public T get(Serializable id) {
		T t = LRUCache.get(id);
		return t;
	}

	@Override
	public void update(T t) {
		LRUCache.put(t.getId(), t);
	}

	@Override
	public void remove(T t) {
		LRUCache.remove(t);
	}

	@Override
	public void clean() {
		LRUCache.clear();
	}
	
	@Override
	public boolean exist(T t) {
		return LRUCache.containsKey(t.getId());
	}
}
