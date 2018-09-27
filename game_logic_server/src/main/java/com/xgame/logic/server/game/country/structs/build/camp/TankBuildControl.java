package com.xgame.logic.server.game.country.structs.build.camp;

import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;

/**
 * 坦克厂
 *2016-8-05  12:22:49
 *@author ye.yuan
 *
 */
public class TankBuildControl  extends CampBuildControl{

	public int getAttributeNode() {
		return AttributeNodeEnum.TANK.ordinal();
	}
}
