package com.xgame.logic.server.core.utils.geometry.data.transform;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;

import io.protostuff.Tag;

/**
 *
 *2016-11-02  12:07:34
 *@author ye.yuan
 *
 */
public class RectTransform extends SpaceTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6817970943259706859L;

	@Tag(6)
	private Vector2 leftUpPoint = new Vector2();
	
	@Tag(7)
	private Vector2 leftDownPoint = new Vector2();
	
	@Tag(8)
	private Vector2 rightUpPoint = new Vector2();
	
	@Tag(9)
	private Vector2 rightDownPoint = new Vector2();
	
	@Tag(10)
	private Vector2 centerPoint = new Vector2();

	public Vector2 getLeftUpPoint() {
		return leftUpPoint;
	}

	public void setLeftUpPoint(Vector2 leftUpPoint) {
		this.leftUpPoint = leftUpPoint;
	}

	public Vector2 getLeftDownPoint() {
		return leftDownPoint;
	}

	public void setLeftDownPoint(Vector2 leftDownPoint) {
		this.leftDownPoint = leftDownPoint;
	}

	public Vector2 getRightUpPoint() {
		return rightUpPoint;
	}

	public void setRightUpPoint(Vector2 rightUpPoint) {
		this.rightUpPoint = rightUpPoint;
	}

	public Vector2 getRightDownPoint() {
		return rightDownPoint;
	}

	public void setRightDownPoint(Vector2 rightDownPoint) {
		this.rightDownPoint = rightDownPoint;
	}

	public Vector2 getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(Vector2 centerPoint) {
		this.centerPoint = centerPoint;
	}
	
}
