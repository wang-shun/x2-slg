package com.xgame.logic.server.game.country.structs.build.camp;

import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;


/**
 * 飞机场
 *2016-8-04  18:19:07
 *@author ye.yuan
 *
 */
public class PlaneBuildControl extends CampBuildControl{

	public int getAttributeNode() {
		return AttributeNodeEnum.PLANE.ordinal();
	}
	
}
