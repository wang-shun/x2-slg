package com.log;

import com.google.common.eventbus.EventBus;



/**
 *订阅监听
 *2016-10-17  18:13:32
 *@author ye.yuan
 *
 */ 
public class LogSubscribe {

	private EventBus bus = new EventBus();

	public LogSubscribe() {
		
	}

	public EventBus getBus() {
		return bus;
	}

	public void setBus(EventBus bus) {
		this.bus = bus;
	}
	
	
	
}
