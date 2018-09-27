package com.xgame.logic.server.game.world.constant;


/**
 * 行军类型 1.采集 2.扎营3占领4攻打玩家5侦查
 * @author jacky.jiang
 *
 */
public enum MarchType {
	
	/**
	 * 默认
	 */
	DEFAULT,
	
	/**
	 *1. 采集
	 */
	EXPLORER,
	
	/**
	 *2. 扎营
	 */
	CAMP,
	
	/**
	 *3. 占领
	 */
	TERRITORY,
	
	/**
	 *4.攻打玩家
	 */
	CITY_FIGHT,
	
	/**
	 * 5.侦查
	 */
	SCOUT,
	
	/**
	 * 6.交易
	 */
	TRADE,
	
	/**
	 * 7.集结防御
	 */
	MARCH_REINFORCE,
	
	/**
	 * 8.集结进攻
	 */
	TEAM_ATTACK,
	
	/**
	 * 9.联盟建造
	 */
	ALLIANCE_BUILD,
	
	/**
	 * 10.联盟仓库
	 */
	ALLIANCE_WAREHOUSE,
	
	/**
	 * 11.协助进攻
	 */
	ALLIANCE_MEMBER_ASSEMBLY,
	
	/**
	 * 12.军团演戏
	 */
	ALLIANCE_TRAIN,
	
}
