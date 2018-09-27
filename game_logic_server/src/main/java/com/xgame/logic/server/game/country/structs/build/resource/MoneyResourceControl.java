package com.xgame.logic.server.game.country.structs.build.resource;

import com.xgame.logic.server.game.constant.CurrencyEnum;


/**
 * 黄金仓库
 * @author jacky.jiang
 *
 */
public class MoneyResourceControl extends ResourceControl {

	@Override
	public int getResourceId() {
		return CurrencyEnum.GLOD.ordinal();
	}
}
