package com.xgame.logic.server.game.country.structs.build.camp;

import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;

/**
 * 战车工厂
 *2016-8-05  12:24:09
 *@author ye.yuan
 *
 */
public class SUVBuildControl extends CampBuildControl{

	public int getAttributeNode() {
		return AttributeNodeEnum.SUV.ordinal();
	}
}
