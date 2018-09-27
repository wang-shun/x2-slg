package com.xgame.logic.server.core.utils.geometry.data.transform;

import java.io.Serializable;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;

import io.protostuff.Tag;

/**
 *
 *2016-10-30  12:08:38
 *@author ye.yuan
 *
 */
public class Transform implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 坐标位置`
	 */
	@Tag(1)
	private Vector2 location = new Vector2(0,0);
	
	public Transform() {
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

}
