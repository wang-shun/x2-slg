package com.xgame.logic.server.core.utils.geometry.data.transform;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;

import io.protostuff.Tag;

/**
 *
 *2016-8-31  20:40:09
 *@author ye.yuan
 *
 */
public class SpriteTransform extends Transform{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5409410116212355324L;

	/**
	 * 
	 */
	@Tag(2)
	private long uid;

	/**
	 * 上次点
	 */
	@Tag(3)
	private Vector2 lastLocation = new Vector2(0,0);

	public SpriteTransform() {
	}
	
	
	
	public SpriteTransform(long uid) {
		this.uid=uid;
	}

	/**
	 * 传送时验证是否在原地踏步
	 * @return
	 */
	public boolean isSameLocation(int x,int y){
		return getLocation().compareTo(new Vector2(x, y))==0?true:false;
	}
	

	public Vector2 getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Vector2 lastLocation) {
		this.lastLocation = lastLocation;
	}
	

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
}
