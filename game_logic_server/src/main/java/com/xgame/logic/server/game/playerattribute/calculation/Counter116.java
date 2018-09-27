package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter116 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.DODGE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.DODGE_PER;
	}

}
