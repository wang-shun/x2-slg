package com.xgame.config.util;

import java.util.HashMap;

/**
 *
 *2016-11-29  18:21:22
 *@author ye.yuan
 *
 */
public class ConfFactory<K,V> extends HashMap<K, V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public V put(K key, V value) {
        return super.put(key, value);
    }
}
