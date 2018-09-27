package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class IDSequance {


	private long uniqueid = 0;

	private short _uniqueid = -1;


	private void initUniqueSequance() {
		uniqueid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUANCE_ID) + IdSequancePrefix.UNIQUE_ID) * 100000;
	}

	public void init() {
		initUniqueSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public synchronized long genId() {
		_uniqueid = addShortValue(_uniqueid);
		if (_uniqueid == 0) {
			initUniqueSequance();
		}
		long id = uniqueid + _uniqueid;
		return id;
	}
}
