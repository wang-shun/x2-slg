package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter108 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.ELECTRICITY_DEFENSE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.ELECTRICITY_DEFENSE_PER;
	}

}
