package com.log.task;

import com.log.template.BaseLog;

/**
 *
 * 2016-7-07 09:29:00
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
