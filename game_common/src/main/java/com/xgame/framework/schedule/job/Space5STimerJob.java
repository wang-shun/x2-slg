package com.xgame.framework.schedule.job;

import com.xgame.framework.schedule.event.Space5STimerEvent;
/**
 *
 *2016-9-18  14:19:28
 *@author ye.yuan
 *
 */
public class Space5STimerJob extends TitckJob{
	

	@Override
	public void action() {
		param.bus.post(new Space5STimerEvent());
	}
	
}
