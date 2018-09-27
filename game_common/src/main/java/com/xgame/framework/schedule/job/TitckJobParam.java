package com.xgame.framework.schedule.job;

import com.google.common.eventbus.EventBus;

/**
 *
 *2016-9-20  20:49:11
 *@author ye.yuan
 *
 */
public class TitckJobParam {

	public EventBus bus = new EventBus();
	
	public void addListener(Object object){
		bus.register(object);
	}
	
}
