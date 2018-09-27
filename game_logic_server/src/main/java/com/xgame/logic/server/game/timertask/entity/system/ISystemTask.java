package com.xgame.logic.server.game.timertask.entity.system;

import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;


/**
 * 任务系统
 * @author jacky.jiang
 * @param <T>
 */
public interface ISystemTask<T extends SystemTimerTaskData> {
	
	/**
	 * 注册系统定时任务
	 * @param taskTime
	 * @param params
	 * @return
	 */
	T register(int taskTime, Object... params);
	
	/**
	 * 获取定时任务处理器
	 * @param player
	 * @param data
	 * @return
	 */
	Runnable getRunnable(SystemTimerTaskData data);
	
	/**
	 * 重置系统任务
	 * @param timerTaskData
	 */
	void resetTimerTask(SystemTimerTaskData timerTaskData);
	
	/**
	 * 取消定时任务
	 * @param data
	 * @return
	 */
	boolean cancelTask(SystemTimerTaskData data);

	/**
	 * 处理服务器重启
	 * @param timerTaskData
	 * @return
	 */
	T dealRestart(T timerTaskData);
}
