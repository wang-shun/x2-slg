package com.xgame.logic.server.game.timertask.entity.system;

import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.AllianceBuildTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;

/**
 * 联盟建筑队列 超级矿到时间消失
 * @author dingpeng.qu
 *
 */
public class AllianceBuildTimerTask extends AbstractSystemTimerTask<SystemTimerTaskData> {
	
	//商城任务id
	public AllianceBuildTimerTask(SystemTimerCommand timerTaskCommand) {
		super(timerTaskCommand);
	}
	
	@Override
	public SystemTimerTaskData getSystemTimerTaskData(Object... params) {
		SystemTimerTaskData systemTimerTaskData = new SystemTimerTaskData();
		AllianceBuildTimerTaskData allianceBuildTimerTaskData = new AllianceBuildTimerTaskData();
		allianceBuildTimerTaskData.setAllianceBuildId((long)params[0]);
		systemTimerTaskData.setParam(allianceBuildTimerTaskData);
		return systemTimerTaskData;
	}
	
	@Override
	public void initSystemTimerTaskData(SystemTimerTaskData t, int taskTime) {
		super.initSystemTimerTaskData(t, taskTime);
	}
	
	@Override
	public boolean onExecute(SystemTimerTaskData data) {
		AllianceBuildTimerTaskData allianceBuildTimerTaskData = (AllianceBuildTimerTaskData)data.getParam();
		return true;
	}
}
