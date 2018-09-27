package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * timer 定时任务处理
 * @author jacky.jiang
 * @param <T>
 */
public interface ITimerTask<T extends TimerTaskData> {
	
	/**
	 * 注册任务队列
	 * @param player
	 * @param cmd
	 * @param taskTime 任务队列执行事件
	 * @param params 任务队列参数(任务队列当中的参数)
	 * @return
	 */
	T register(Player player, int cmd, int taskTime, Object param) ;
	
	/**
	 * 注册任务队列
	 * @param player
	 * @param taskTime 任务队列执行事件
	 * @param params 任务队列参数
	 * @return
	 */
	T register(Player player, int taskTime, Object params) ;
	
	/**
	 * 返回类型
	 * @return
	 */
	TimerTaskCommand getTimerTaskCommand();
	
	/**
	 * 手动取消队列
	 * @param player
	 * @param data
	 * @return
	 */
	boolean cancelTask(Player player, TimerTaskData data);
	
	/**
	 * 手动加速队列
	 * @param player
	 * @param data
	 * @param time(加速队列节省的时间)
	 * @return
	 */
	boolean speedUp(Player player, TimerTaskData data, int time);
	
	/**
	 * 手动重置队列
	 * @param player
	 * @param data
	 */
	public void resetTimerTask(Player player, TimerTaskData data);
	
	/**
	 * 判断队列是否已满
	 * @param player
	 * @param count
	 * @return
	 */
	boolean checkQueueCapacityMax(Player player);
	
	/**
	 * 获取定时任务处理器
	 * @param player
	 * @param data
	 * @return
	 */
	Runnable getRunnable(Player player, TimerTaskData data);
	
	/**
	 * 重启处理
	 * @param timerTaskData
	 * @return
	 */
	TimerTaskData dealRestart(TimerTaskData timerTaskData);
}
