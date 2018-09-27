package com.xgame.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *2016-10-20  11:39:55
 *@author ye.yuan
 *
 */
public class BlockingHashBaseMap<R,C,V> {
	
	
	private Map<R, Map<C, V>> map = new ConcurrentHashMap<R, Map<C, V>>();
	
	public V get(R x, C y) {
		
		Map<C, V> innerMap = map.get(x);
		if(innerMap != null) {
			return innerMap.get(y);
		}
		return null;
	}
	
	public Map<C, V> row(R x) {
		return map.get(x);
	}
	
	public V[] toRowArray(R x,V[] v) {
		
		Map<C, V> innerMap = map.get(x);
		if(innerMap != null) {
			innerMap.values().toArray(v);
		}
		
			return map.get(x).values().toArray(v);
	}
	
	public boolean contains(R x, C y) {
		Map<C, V> innerMap = map.get(x);
		if (innerMap != null) {
			V v = innerMap.get(y);
			if (v != null) {
				return true;
			}
		}
		return false;
	}
	
	
	public void put(R x, C y,V object) {
		Map<C, V> innerMap = map.get(x);
		if(innerMap == null) {
			innerMap = new HashMap<C, V>();
			this.map.put(x, innerMap);
		}
		
		innerMap.put(y, object);
		
	}
	
	public V remove(C x, R y) {
		Map<C, V> innerMap = map.get(x);
		if(innerMap != null) {
			return innerMap.remove(y);
		}
		return null;
	}
	

	public Map<R, Map<C, V>> getMap() {
		return this.map;
	}

	public void setMap(Map<R, Map<C, V>> map) {
		this.map = map;
	}
	
}
