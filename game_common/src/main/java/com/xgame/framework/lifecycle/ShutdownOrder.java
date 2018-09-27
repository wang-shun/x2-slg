package com.xgame.framework.lifecycle;

/**
 * 关闭顺序
 * @author jacky.jiang
 *
 */
public enum ShutdownOrder {
	
	
	
	/**
	 * 线程管理器
	 */
	COMMAND_PROCESSOR,
	
	/**
	 * 缓存管理器
	 */
	DBCACHE,
	
	/**
	 * 定时任务管理器
	 */
	SCHEDULER,
	
	/**
	 * 系统定时任务
	 */
	SYSTEM_TIMER,
	
	/**
	 * 玩家定时任务
	 */
	GAME_TIMER,
	
	/**
	 * 日志关闭
	 */
	LOG,

	/**
	 * 野外战报推送
	 */
	WILD_BATTLE_REPORT_PUSH,
	
	/**
	 * redis
	 */
	REDIS_CLIENT,

	/**
	 * redis全局配置
	 */
	GLOBAL_REDIS_CLIENT,
	
	
}	
