package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class TimerTaskSequance {

	private long timerid = 0;

	private short _timerid = -1;

	private void initTimerSequance() {
		timerid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUANCE_TIMER_TASK) +IdSequancePrefix.TIMER_ID) * 100000;
	}

	public void init() {
		initTimerSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public synchronized long genTimerId() {
		_timerid = addShortValue(_timerid);
		if (_timerid == 0) {
			initTimerSequance();
		}
		return timerid + _timerid;
	}
}
