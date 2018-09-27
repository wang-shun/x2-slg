package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter102 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.LOAD;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.LOAD_PER;
	}

}
