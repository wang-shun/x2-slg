package com.xgame.logic.server.game.world.constant;

/**
 * 常量保存
 * @author jacky.jiang
 *
 */
public class WorldConstant {

	public static final int QUERY_BATTLE_TEAM_ATTACK = 1;
	public static final int QUERY_BATTLE_TEAM_DEFEND = 2;
	
	/**
	 * 集结进攻时间
	 */
	public static int[] ASSEMBLE_WAIT_TIME = new int[]{300, 900, 1800, 3600, 14400, 28800};
	
	
	/**
	 * 1(1-10)军团排名
	 */
	public static final int ALLIANCE_TERRITORY_TYPE_1 = 1;
	
	/**
	 * 2(11-20)军团排名
	 */
	public static final int ALLIANCE_TERRITORY_TYPE_2 = 2;
	
	
	/**
	 * 3(21-30)军团排名
	 */
	public static final int ALLIANCE_TERRITORY_TYPE_3 = 3;
	
	/**
	 * x的坐标系
	 */
	public static final int X_GRIDNUM = 512;
	
	/**
	 * y的坐标系
	 */
	public static final int Y_GRIDNUM = 512;
	
	/**
	 * 每个格子显示联盟领土数量
	 */
	public static final int ALLIANCE_TERRITORY_NUM = 5;
	
	// 屏幕格子宽度
	public static final int VIEW_GRID_SIZE = 12;
	
	// 战斗10秒钟
	public static final int BATTLE_TIME = 10 * 1000;
	
	
	public static final int TERRITORY_QUERY_TYPE_QUERY = 1;
	public static final int TERRITORY_QUERY_TYPE_PUSH = 2;
	
	//  迁城扣除道具类型 - 道具
	public static final int MOVE_CITY_DEDUCT_TYPE_ITEM = 1;
	// 迁城扣除道具类型-钻石
	public static final int MOVE_CITY_DEDUCT_TYPE_DIAMOND = 2;
}
