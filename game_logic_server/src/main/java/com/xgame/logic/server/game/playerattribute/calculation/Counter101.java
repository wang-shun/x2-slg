package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter101 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.POWER;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.POWER_PER;
	}

}
