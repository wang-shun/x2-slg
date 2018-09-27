package com.xgame.logic.server.game.timertask.entity.system;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.GameEventTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;

/**
 * 系统事件定时任务
 * 开启事件后定时关闭,执行事件关闭逻辑
 * @author dingpeng.qu
 *
 */
public class GameEventTimerTask extends AbstractSystemTimerTask<SystemTimerTaskData>{

	//事件任务ID
	public GameEventTimerTask(SystemTimerCommand timerTaskCommand) {
		super(timerTaskCommand);
	}
	
	@Override
	public SystemTimerTaskData getSystemTimerTaskData(Object... params) {
		SystemTimerTaskData systemTimerTaskData = new SystemTimerTaskData();
		GameEventTimerTaskData gameEventTimerTaskData = new GameEventTimerTaskData();
		gameEventTimerTaskData.setEventId((long)params[0]);
		systemTimerTaskData.setParam(gameEventTimerTaskData);
		return systemTimerTaskData;
	}
	
	@Override
	public void initSystemTimerTaskData(SystemTimerTaskData t, int taskTime) {
		super.initSystemTimerTaskData(t, taskTime);
	}
	
	@Override
	public boolean onExecute(SystemTimerTaskData data) {
		GameEventTimerTaskData gameEventTimerTaskData = (GameEventTimerTaskData)data.getParam();
		//执行定时器逻辑
		InjectorUtil.getInjector().eventManager.stopEvent(gameEventTimerTaskData.getEventId());
		return true;
	}
}
