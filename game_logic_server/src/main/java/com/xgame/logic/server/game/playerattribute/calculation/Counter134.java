package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter134 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.LOAD_CONSUME;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.LOAD_CONSUME_PER;
	}

}
