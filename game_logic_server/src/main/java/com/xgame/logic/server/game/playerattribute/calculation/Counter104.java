package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter104 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.ATTACK;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.DAMAGE_PER;
	}

}
