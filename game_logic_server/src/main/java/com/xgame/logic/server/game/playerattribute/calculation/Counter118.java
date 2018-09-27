package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter118 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.CRITICAL;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.CRITICAL_PER;
	}

}
