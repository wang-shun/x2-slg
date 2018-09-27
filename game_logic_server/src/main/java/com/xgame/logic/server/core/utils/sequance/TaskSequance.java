package com.xgame.logic.server.core.utils.sequance;


import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

@Component
public class TaskSequance {


	private long taskid = 0;

	private short _taskid = -1;

	private void initItemSequance() {
		taskid = (InjectorUtil.getInjector().redisClient.incr(DBKey.SEQUANCE_TASK) + IdSequancePrefix.TASK_ID) * 100000;
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

	public long genTaskId() {
		_taskid = addShortValue(_taskid);
		if (_taskid == 0) {
			initItemSequance();
		}
		return taskid + _taskid;
	}
}
