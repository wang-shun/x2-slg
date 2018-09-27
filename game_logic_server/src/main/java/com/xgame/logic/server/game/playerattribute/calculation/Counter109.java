package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter109 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.ENERGY_DAMAGE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.ENERGY_DAMAGE_PER;
	}

}
