package com.log.dispatcher;

import com.log.template.BaseLog;

/**
 *
 *2016-10-18  11:11:08
 *@author ye.yuan
 *
 */
public class LogSubscribeEvent {

	protected BaseLog baseLog;
	
	public LogSubscribeEvent(BaseLog baseLog) {
		this.baseLog = baseLog;
	}

	public BaseLog getBaseLog() {
		return baseLog;
	}

	public void setBaseLog(BaseLog baseLog) {
		this.baseLog = baseLog;
	}
}
