package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter131 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.RADAR_INTENSITY;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.RADAR_INTENSITY_PER;
	}

}
