package com.xgame.logic.server.game.war.constant;

/**
 * 战斗结束奖励结算
 * @author jacky.jiang
 *
 */
public enum WarType {
	
	/**
	 * 搜索战斗
	 */
	COUNTRY_SEARCH(101, true),
	
	/**
	 * 攻打玩家
	 */
	WORLD_CITY(102, false),
	
	/**
	 * 采集
	 */
	EXPLORER(103, false),
	
	/**
	 * 集结进攻
	 */
	TEAM_ATTACK(104, false),
	
	/**
	 * 攻打营地
	 */
	CAMP(105, false),
	
	/**
	 * 领地战斗
	 */
	TERRITORY(106, false),
	
	/**
	 * 联盟活动
	 */
	ALLIANCE_ACTIVITY_FIGHT(107, false),
	
	/**
	 * RVR
	 */
	RVR_FIGHT(108, false),
	
	/**
	 * 怪物入侵
	 */
	MONSTER_INVASION(109, false),
	
	/**
	 * 异星boss战斗
	 */
	BOSS_FIGHT(110, false),
	
	/**
	 * 副本战斗
	 */
	FUBEN_FIGHT(111, true),
	
	/**
	 * 地图野怪战斗
	 */
	WORLD_MOSTER(112, false),
	;
	
	private int configId;
	
	private boolean rts;
	
	WarType(int configId, boolean rts) {
		this.configId = configId;
		this.rts = rts;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public boolean isRts() {
		return rts;
	}

	public void setRts(boolean rts) {
		this.rts = rts;
	}
}
