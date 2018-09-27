package com.xgame.logic.server.core.gamelog.task;

import com.xgame.logic.server.core.gamelog.logs.BaseLog;

/**
 *
 * 
 * @author ye.yuan
 *
 */
public class FileLogTask implements Runnable {
	
	BaseLog log;

	public FileLogTask(BaseLog log) {
		this.log = log;
	}

	public void run() {
		this.log.logToFile();
	}
}
