package com.xgame.logic.server.game.world.entity;

/**
 * 地图数据
 * @author jacky.jiang
 *
 */
public class MapJsonData {
	
	private int x;
	private int y;
	private int groundId;
	private int surfaceId;
	private boolean canMove;

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getGroundId() {
		return groundId;
	}

	public void setGroundId(int groundId) {
		this.groundId = groundId;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public int getSurfaceId() {
		return surfaceId;
	}

	public void setSurfaceId(int surfaceId) {
		this.surfaceId = surfaceId;
	}
}
