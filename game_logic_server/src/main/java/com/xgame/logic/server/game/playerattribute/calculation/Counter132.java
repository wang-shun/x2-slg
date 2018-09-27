package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter132 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.RADIUS;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.RADIUS_PER;
	}

}
