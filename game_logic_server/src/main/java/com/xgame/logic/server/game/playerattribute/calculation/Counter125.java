package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter125 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.SPEED_GATHER;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.SPEED_GATHER_PER;
	}

}
