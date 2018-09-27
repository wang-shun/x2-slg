package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter105 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.DEFENSE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.DEFENSE_PER;
	}

}
