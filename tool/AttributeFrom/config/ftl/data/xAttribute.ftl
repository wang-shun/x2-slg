package com.xgame.data.attribute;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *每条属性对象  停用后不再参与计算 但值保留
 *@author gameToos
 *
 */
public class ${className} implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public int enumType;
	
	
	public boolean enable;
	
	/**
	 * 
	 */
	public Map<Integer, SystemAttribute> attributes = new ConcurrentHashMap<>();
	
	
	public ${className}(int type, boolean enable) {
		super();
		this.enumType = type;
		this.enable = enable;
	}
	
	public long toAttribute(){
		long value = 0l;
		Iterator<SystemAttribute> iterator = attributes.values().iterator();
		while (iterator.hasNext()) {
			SystemAttribute systemAttribute = (SystemAttribute) iterator.next();
			value+=systemAttribute.value;
		}
		return value;
	}
}
