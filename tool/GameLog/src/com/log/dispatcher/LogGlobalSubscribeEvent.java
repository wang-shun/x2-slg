package com.log.dispatcher;

import com.log.template.BaseLog;

/**
 *
 *2016-10-18  16:24:40
 *@author ye.yuan
 *
 */
public class LogGlobalSubscribeEvent {

	protected BaseLog baseLog;
	
	public LogGlobalSubscribeEvent(BaseLog baseLog) {
		this.baseLog = baseLog;
	}

	public BaseLog getBaseLog() {
		return baseLog;
	}

	public void setBaseLog(BaseLog baseLog) {
		this.baseLog = baseLog;
	}
	
}
