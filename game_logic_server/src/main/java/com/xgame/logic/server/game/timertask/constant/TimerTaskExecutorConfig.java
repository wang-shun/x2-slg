package com.xgame.logic.server.game.timertask.constant;

/**
 * 任务队列线程池配置
 * @author jacky.jiang
 *
 */
public interface TimerTaskExecutorConfig {
	
	
	/**
	 * 任务队列线程池
	 */
	public static final String  TIMER_TASK_EXECUTOR_NAME = "TIMER_TASK_EXECUTOR";
	
	/**
	 * 核心线程大小
	 */
	public static final int CORE_THREAD_SIZE = 8;
	
	/**
	 * 最大线程池大小
	 */
	public static final int MAX_THREAD_SIZE = 64;
}
