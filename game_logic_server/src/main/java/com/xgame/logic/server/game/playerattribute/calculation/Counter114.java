package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter114 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.LASER_DEFENSE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.LASER_DEFENSE_PER;
	}

}
