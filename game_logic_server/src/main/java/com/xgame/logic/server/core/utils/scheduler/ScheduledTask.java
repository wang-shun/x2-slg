package com.xgame.logic.server.core.utils.scheduler;

/**
 * 定时任务接口
 * @author jacky.jiang
 */
public interface ScheduledTask extends Runnable {

	/**
	 * 获取当前任务的任务名
	 * @return
	 */
	String getName();
}
