package com.xgame.logic.server.game.war.entity.report;

import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 * 
 * @author jacky.jiang
 *
 */
public class WarSoldierInfo {
	
	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 建筑id(防守驻军)
	 */
	private int buildUid;
	
	/**
	 * 士兵
	 * {@linkplain Soldier}
	 */
	private long uid;
	
	/**
	 * 所在位置
	 */
	private int index;
	
	/**
	 * 初始数量
	 */
	private int num;
	
	/**
	 * 伤病数量
	 */
	private int deadNum;
	
	/**
	 * 获取剩余数量
	 * @return
	 */
	public int getPlusNum() {
		return num - deadNum;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(int buildUid) {
		this.buildUid = buildUid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getDeadNum() {
		return deadNum;
	}

	public void setDeadNum(int deadNum) {
		this.deadNum = deadNum;
	}
	
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
