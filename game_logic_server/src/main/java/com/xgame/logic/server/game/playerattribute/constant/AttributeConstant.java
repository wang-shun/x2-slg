package com.xgame.logic.server.game.playerattribute.constant;

import com.xgame.logic.server.game.country.structs.build.BuildFactory;

/**
 * 属性模块常量
 * @author zehong.he
 *
 */
public interface AttributeConstant {

	/**
	 * building 配置表v1标识属性时的id
	 */
	int[] BUILD_ATTR_V1_IDS = {BuildFactory.CONSULATE.getTid(),BuildFactory.RADAR.getTid(),BuildFactory.MILITARY_CAMP.getTid(),BuildFactory.DEFEBSE_SOLDIER.getTid()};
	
	/**
	 * building 配置表v2标识属性时的id
	 */
	int[] BUILD_ATTR_V2_IDS = {BuildFactory.MAIN.getTid()};
	
	/**
	 * 玩家特殊处理属性（客户端获取该属性时给出最终属性值）
	 */
	int[] PLAYER_ATTR_SPECIAL_ID = {AttributesEnum.CASH_SAFEGUARD_RATE.getId(),
			AttributesEnum.EARTH_SAFEGUARD_RATE.getId(),
			AttributesEnum.OIL_SAFEGUARD_RATE.getId(),
			AttributesEnum.STEEL_SAFEGUARD_RATE.getId(),
			AttributesEnum.ARMY_SPY_MARCH_SPEED.getId(),
			AttributesEnum.MATCH_ARMY_MAX_NUM.getId(),
			AttributesEnum.MATCH_QUEUE.getId(),
			AttributesEnum.CONCENTRATE_ARMY.getId(),
			AttributesEnum.BUILDING_QUEUE.getId(),
			AttributesEnum.TECH_QUEUE.getId(),
			AttributesEnum.GUARD_MAX_NUM.getId(),
			AttributesEnum.HELP_REDUCE_BUILDING_TIME.getId()};
	
	
	/**
	 * 属性id>=300为玩家属性
	 */
	int PLAYER_ATTRIBUTE_MINID_FLAG = 300;
}
