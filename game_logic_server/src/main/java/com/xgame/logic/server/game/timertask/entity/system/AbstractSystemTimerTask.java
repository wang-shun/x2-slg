package com.xgame.logic.server.game.timertask.entity.system;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;
import com.xgame.utils.TimeUtils;


/**
 * 抽象timer类
 * @author jacky.jiang
 * @param <T>
 */
@Slf4j
public abstract class AbstractSystemTimerTask<T extends SystemTimerTaskData> implements ISystemTask<SystemTimerTaskData>{
	
	private SystemTimerCommand timerTaskCommand;
	
	public AbstractSystemTimerTask(SystemTimerCommand timerTaskCommand) {
		this.timerTaskCommand = timerTaskCommand;
	}

	@Override
	public T register(int taskTime, Object... params) {
		
		// 创建timer加锁
		synchronized (this) {
			T t = getSystemTimerTaskData(params);
			
			// 初始化timer数据
			initSystemTimerTaskData(t, taskTime);
			
			// 添加成功回调
			if(onAdd(t)) {
				// 注册到timer管理器
				InjectorUtil.getInjector().timerTaskManager.addSystemTimerTask(t);
				return t;
			} else {
				// 添加不处理
				return null;
			}	
		}
	}

	/**
	 * 返回timerTask
	 * @param params
	 * @return
	 */
	public abstract T getSystemTimerTaskData(Object... params);
	
	
	/**
	 * 初始化timerTaskData
	 * @param player
	 * @param t
	 * @param cmd
	 * @param taskTime
	 */
	public void initSystemTimerTaskData(T t, int taskTime) {
		int currentTime = TimeUtils.getCurrentTime();
		t.setTaskId(InjectorUtil.getInjector().timerTaskSequance.genTimerId());
		t.setQueueId(timerTaskCommand.ordinal());
		t.setTaskTime(taskTime);
		t.setTriggerTime(currentTime + taskTime);
		t.setStartTime(currentTime);
		t.setCreateTime(currentTime);
	}

	/**
	 * 任务队列到期执行
	 * @param player
	 * @param data
	 * @return
	 */
	private void execute(SystemTimerTaskData data) {
		synchronized (data) {
			// 任务执行
			try {
				if(onExecute(data)) {
					// 任务结束移除任务
					if(data.isOver()) {
						InjectorUtil.getInjector().timerTaskManager.removeSystemTimerTask(data);	
						onRemove(data);
					} else {
						// 重置任务
						if(onReset(data)) {
							InjectorUtil.getInjector().timerTaskManager.resetSystemTimerTask(data);;
						}
					}
				}
			} catch(Exception e) {
				onException(data);
				log.error("任务队列执行报错", e);
			}
		}
	}

	/**
	 * 获得任务执行
	 * @return
	 */
	@Override
	public Runnable getRunnable(SystemTimerTaskData data) {
		return new Runnable() {
			public void run() {
				execute(data);
			}
		};
	}
	
	public boolean onAdd(SystemTimerTaskData data) {
		return true;
	}
	
	public boolean onExecute(SystemTimerTaskData data) {
		return true;
	}
	
	public boolean onReset(SystemTimerTaskData data) {
		return true;
	}
	
	public boolean onRemove(SystemTimerTaskData data) {
		return true;
	}

	@Override
	public boolean cancelTask(SystemTimerTaskData data) {
		InjectorUtil.getInjector().timerTaskManager.removeSystemTimerTask(data);
		return true;
	}
	
	/**
	 * 异常处理
	 * @param player
	 * @param data
	 */
	public void onException(SystemTimerTaskData data) {
		InjectorUtil.getInjector().dbCacheService.delete(data);
		log.error("任务队列异常:{}", data.toString());
	}

	@Override
	public void resetTimerTask(SystemTimerTaskData timerTaskData) {
		// 重置队列信息
		InjectorUtil.getInjector().timerTaskManager.resetSystemTimerTask(timerTaskData);
	}

	@Override
	public SystemTimerTaskData dealRestart(SystemTimerTaskData timerTaskData) {
		return null;
	}
}
