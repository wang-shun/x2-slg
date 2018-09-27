package com.xgame.logic.server.game.war.entity.report;

/**
 * 杀敌数
 * @author jacky.jiang
 *
 */
public class WarPlayerReport {
	
	/**
	 * 杀敌数
	 */
	private int killedNum;
	
	/**
	 * 死亡数量
	 */
	private int deadNum;
	
	/**
	 * 总伤害
	 */
	private long damage;
	
	/**
	 * 玩家id
	 */
	private long playerId;

	public int getKilledNum() {
		return killedNum;
	}

	public void setKilledNum(int killedNum) {
		this.killedNum = killedNum;
	}

	public int getDeadNum() {
		return deadNum;
	}

	public void setDeadNum(int deadNum) {
		this.deadNum = deadNum;
	}

	public long getDamage() {
		return damage;
	}

	public void setDamage(long damage) {
		this.damage = damage;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
	
}
