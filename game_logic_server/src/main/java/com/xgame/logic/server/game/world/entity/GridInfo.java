package com.xgame.logic.server.game.world.entity;


/**
 * 网格信息
 * @author jacky.jiang
 *
 */
public class GridInfo {
	
	/**
	 * x 坐标
	 */
	private int x;
	
	/**
	 * y 坐标
	 */
	private int y;
	
	/**
	 * 
	 */
	private long uid;
	

	GridInfo(int x, int y, long uid) {
		super();
		this.x = x;
		this.y = y;
		this.uid = uid;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
}
