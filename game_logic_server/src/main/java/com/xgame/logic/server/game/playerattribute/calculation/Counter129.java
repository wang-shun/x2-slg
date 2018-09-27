package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter129 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.SEEKING_NUM;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.SEEKING_NUM_PER;
	}

}
