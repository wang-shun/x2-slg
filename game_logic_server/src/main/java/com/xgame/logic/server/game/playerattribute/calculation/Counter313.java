package com.xgame.logic.server.game.playerattribute.calculation;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
public class Counter313 extends AttributeCounter {

	@Override
	public AttributesEnum getSelfAttribute() {
		return AttributesEnum.STEEL_SAFEGUARD_RATE;
	}

	@Override
	public AttributesEnum getRelationAttribute() {
		return AttributesEnum.STEEL_SAFEGUARD_RATE;
	}

	@Override
	public double getFinalValue(Player player,AttributeNodeEnum node,double initValue,double initAddValue){
		double selfValue = getValue(player,getSelfAttribute(),node,initAddValue);
		return formula3(selfValue,initValue);
	}
}
