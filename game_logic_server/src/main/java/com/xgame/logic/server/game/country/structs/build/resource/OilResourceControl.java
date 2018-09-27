package com.xgame.logic.server.game.country.structs.build.resource;

import com.xgame.logic.server.game.constant.CurrencyEnum;



/**
 * 石油仓库
 * @author jacky.jiang
 *
 */
public class OilResourceControl extends ResourceControl {

	@Override
	public int getResourceId() {
		return CurrencyEnum.OIL.ordinal();
	}
}
