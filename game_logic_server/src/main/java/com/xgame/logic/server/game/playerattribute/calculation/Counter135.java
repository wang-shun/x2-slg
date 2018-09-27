package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter135 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.SPEED_TEAM_ATTACK;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.SPEED_MASS_PER;
	}

}
