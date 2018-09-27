package com.xgame.framework.schedule;

import org.quartz.Job;

/**
 * 延迟任务
 * @author jacky.jiang
 *
 */
public interface SchedulerJob extends Job {
	
	/**
	 * 任务名称
	 * @return
	 */
	String name();
	
	/**
	 * 执行的cron表达式
	 * @return
	 */
	String scheduerCron();
	
}
