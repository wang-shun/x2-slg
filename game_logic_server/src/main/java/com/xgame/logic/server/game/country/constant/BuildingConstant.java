package com.xgame.logic.server.game.country.constant;

import com.xgame.logic.server.game.country.structs.build.BuildFactory;

/**
 * 建筑常量
 * @author jacky.jiang
 *
 */
public class BuildingConstant {
	
	/**
	 * 需要被侦查的建筑
	 */
	public static final int[] SCOUT_BUILDING = new int[]{BuildFactory.MAIN.getTid(), BuildFactory.DEFEBSE_SOLDIER.getTid(), BuildFactory.TOWER1301.getTid(), BuildFactory.TOWER1302.getTid(), BuildFactory.TOWER1303.getTid(), BuildFactory.TOWER1304.getTid(), BuildFactory.TOWER1305.getTid()};

	/**
	 * 塔的建筑
	 */
	public static final int[] TOWER_BUILDING = new int[]{BuildFactory.TOWER1301.getTid(), BuildFactory.TOWER1302.getTid(), BuildFactory.TOWER1303.getTid(), BuildFactory.TOWER1304.getTid(), BuildFactory.TOWER1305.getTid()};
	
	/**
	 * 仓库建筑
	 */
	public static final int[] RESTORE_BUILDING = new int[]{BuildFactory.MONEY_RESOURCE.getTid(), BuildFactory.RARE_RESOURCE.getTid(), BuildFactory.OIL_RESOURCE.getTid(), BuildFactory.STEEL_RESOURCE.getTid()};

	/**
	 * 障碍类型建筑
	 */
	public static final int[] OBSTACLE_BUILDING = new int[]{BuildFactory.OBSTRUCT1700.getTid(), BuildFactory.OBSTRUCT1701.getTid(), BuildFactory.OBSTRUCT1701.getTid()};
}
