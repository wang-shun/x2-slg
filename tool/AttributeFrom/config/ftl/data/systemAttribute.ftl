package com.xgame.data.attribute;

import java.io.Serializable;

/**
 *
 *某个系统某条属性的对象
 *@author gameTools
 *
 */
public class ${className} implements Serializable{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	public boolean enable;
	
	public ${className}(boolean enable, long value) {
		super();
		this.enable = enable;
		this.value = value;
	}

	public volatile long value;
	
}
