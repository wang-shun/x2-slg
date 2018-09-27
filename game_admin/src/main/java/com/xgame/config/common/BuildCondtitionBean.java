package com.xgame.config.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *2016-7-25  11:24:42
 *@author ye.yuan
 *
 */
public class BuildCondtitionBean {

	private Map<Integer, Integer> cache = new HashMap<>();

	public BuildCondtitionBean(String str) {
		String[] split2 = str.split("_");
		for(int i=0;i<split2.length;i++){
			cache.put(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
		}
	}

	public Map<Integer, Integer> getCache() {
		return cache;
	}

	public void setCache(Map<Integer, Integer> cache) {
		this.cache = cache;
	}
	
}
