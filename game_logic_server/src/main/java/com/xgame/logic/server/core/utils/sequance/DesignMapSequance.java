package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

/**
 * 图纸id
 * @author jacky.jiang
 *
 */
@Component
public class DesignMapSequance {
	
	private long designMap = 0;

	private short _designMap = -1;

	private void initDesignMapSequance() {
		designMap = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUANCE_DESIGN_MAP) + IdSequancePrefix.DESIGN_MAP_ID) * 100000;
	}

	public void init() {
		initDesignMapSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genDesignMapId() {
		_designMap = addShortValue(_designMap);
		if (_designMap == 0) {
			initDesignMapSequance();
		}
		return designMap + _designMap;
	}
}
