package com.xgame.logic.server.game.country.structs.build.resource;

import com.xgame.logic.server.game.constant.CurrencyEnum;


/**
 * 稀土仓库
 * @author jacky.jiang
 *
 */
public class RareResourceCountrol extends ResourceControl {

	@Override
	public int getResourceId() {
		return CurrencyEnum.RARE.ordinal();
	}
}
