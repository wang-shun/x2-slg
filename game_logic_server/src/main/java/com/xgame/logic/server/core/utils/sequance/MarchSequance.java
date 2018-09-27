package com.xgame.logic.server.core.utils.sequance;


import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class MarchSequance {


	private long marchId = 0;

	private short _marchId = -1;

	private void initItemSequance() {
		marchId = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUENCE_MARCH) + IdSequancePrefix.MARCH_ID) * 100000;
	}

	public void init() {
		initItemSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genMarchId() {
		_marchId = addShortValue(_marchId);
		if (_marchId == 0) {
			initItemSequance();
		}
		return marchId + _marchId;
	}
}
