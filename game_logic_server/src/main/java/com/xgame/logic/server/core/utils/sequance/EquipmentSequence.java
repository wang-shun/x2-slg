package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class EquipmentSequence {


	private long equipmentid = 0;

	private short _equipmentid = -1;

	private void initEquipmentSequance() {
		equipmentid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUENCE_EQUIPMENT) + IdSequancePrefix.EQUIPMENT_ID) * 100000;
	}

	public void init() {
		initEquipmentSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genEquipmentId() {
		_equipmentid = addShortValue(_equipmentid);
		if (_equipmentid == 0) {
			initEquipmentSequance();
		}
		return equipmentid + _equipmentid;
	}
}
