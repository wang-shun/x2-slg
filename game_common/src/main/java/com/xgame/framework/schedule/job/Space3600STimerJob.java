package com.xgame.framework.schedule.job;

import com.xgame.framework.schedule.event.Space3600STimerEvent;

/**
 *
 *2016-9-18  14:33:28
 *@author ye.yuan
 *
 */
public class Space3600STimerJob extends TitckJob{

	@Override
	public void action() {
		param.bus.post(new Space3600STimerEvent());
	}
	
}
