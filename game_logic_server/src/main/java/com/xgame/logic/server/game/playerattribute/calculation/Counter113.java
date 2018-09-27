package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter113 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.LASER_DAMAGE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.LASER_DAMAGE_PER;
	}

}
