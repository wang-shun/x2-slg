package com.xgame.logic.server.game.world.entity.sprite;

import com.xgame.logic.server.core.db.redis.JBaseData;

import io.protostuff.Tag;


/**
 * 玩家精灵信息
 * @author jacky.jiang
 *
 */
public class PlayerSprite extends WorldSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5092141956170544444L;

	/**
	 * 玩家等级
	 */
	@Tag(11)
	private int playerLevel;
	
	/**
	 * 保护结束时间
	 */
	@Tag(12)
	private long shieldTime;
	
	/**
	 * 防守组队信息
	 */
	@Tag(13)
	private long defendTeamId;
	
	/**
	 * 野外基地被打时间
	 */
	@Tag(14)
	private long attackTime;

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public long getShieldTime() {
		return shieldTime;
	}

	public void setShieldTime(long shieldTime) {
		this.shieldTime = shieldTime;
	}

	public long getDefendTeamId() {
		return defendTeamId;
	}

	public void setDefendTeamId(long defendTeamId) {
		this.defendTeamId = defendTeamId;
	}

	public long getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(long attackTime) {
		this.attackTime = attackTime;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = super.toJBaseData();
		jbaseData.put("playerLevel", playerLevel);
		jbaseData.put("shieldTime", shieldTime);
		jbaseData.put("defendTeamId", defendTeamId);
		jbaseData.put("attackTime", attackTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		super.fromJBaseData(jBaseData);
		this.playerLevel = jBaseData.getInt("playerLevel", 0);
		this.shieldTime = jBaseData.getLong("shieldTime", 0);
		this.defendTeamId = jBaseData.getLong("defendTeamId", 0);
		this.attackTime = jBaseData.getLong("attackTime", 0);
	}
}
