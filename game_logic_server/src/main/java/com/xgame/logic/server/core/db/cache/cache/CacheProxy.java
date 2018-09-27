package com.xgame.logic.server.core.db.cache.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;


/**
 * 缓存代理类
 * @author jacky.jiang
 * @param <T>
 */
@Slf4j
public abstract class CacheProxy<T extends IEntity<?>> implements ICacheProxy<T> {

	/**
	 * 缓存
	 */
	private Map<Serializable, T> entityCacheMap = new ConcurrentHashMap<Serializable, T>();
	
	@Override
	public void add(T t) {
		entityCacheMap.put(t.getId(), t);
	}

	@Override
	public T get(Serializable id) {
		T t = entityCacheMap.get(id);
		return t;
	}

	@Override
	public void update(T t) {
		entityCacheMap.put(t.getId(), t);
	}

	@Override
	public void remove(T t) {
		entityCacheMap.remove(t.getId());
	}

	@Override
	public void clean() {
		entityCacheMap.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(String value) {
		Object obj = null;
		try {
			obj = this.getProxyClass().newInstance();
			JBaseData jBaseData = JsonUtil.fromJSON(value, JBaseData.class);
			((JBaseTransform)obj).fromJBaseData(jBaseData);
		} catch (Exception e) {
			log.error("反序列化异常,底层cache:{}", this.getProxyClass());
		} 
		return (T)obj;
	}

	@Override
	public String serialize(T t) {
		return JsonUtil.toJSON(((JBaseTransform)t).toJBaseData());
	}

	@Override
	public boolean exist(T t) {
		return entityCacheMap.containsKey(t.getId());
	}
	
	public Map<Serializable, T> getEntityCache() {
		return entityCacheMap;
	}
	
}
