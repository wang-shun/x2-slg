package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter133 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.POWER_CONSUME;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.POWER_CONSUME_PER;
	}

}
