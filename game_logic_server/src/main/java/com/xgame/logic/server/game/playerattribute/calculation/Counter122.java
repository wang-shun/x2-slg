package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter122 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.SPEED_BASE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.SPEED_BASE_PER;
	}

}
