package com.xgame.logic.server.game.world.constant;


/**
 * 行军类型
 * @author jacky.jiang
 *
 */
public enum MarchState {

	DEFALUT,
	
	/**
	 * 1.出征
	 */
	MARCH,
	
	/**
	 * 2.占领
	 */
	OCCUPY,
	
	/**
	 * 3.返回
	 */
	BACK,
	
	/**
	 * 4.结束
	 */
	OVER,
	
	/**
	 * 5.占领中
	 */
	OCCUPYING,
	
	/**
	 * 6.采集中
	 */
	COLLECTING,
	
	/**
	 * 7.集结
	 */
	MASS,
	
	/**
	 * 8.战斗中
	 */
	FIGHT,
	
	/**
	 * 9.集合行军中
	 */
	GATHER_MARCH
}
