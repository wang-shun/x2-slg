package com.xgame.framework.schedule.job;

import com.xgame.framework.schedule.event.Space1STimerEvent;

/**
 *
 *2016-9-22  15:23:36
 *@author ye.yuan
 *
 */
public class Space1STimerJob extends TitckJob{

	@Override
	public void action() {
		param.bus.post(new Space1STimerEvent());
	}

}
