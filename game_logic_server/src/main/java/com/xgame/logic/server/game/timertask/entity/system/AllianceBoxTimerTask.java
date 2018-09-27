package com.xgame.logic.server.game.timertask.entity.system;

import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.AllianceBoxTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;


/**
 * 科技升级队列
 * @author jacky.jiang
 *
 */
public class AllianceBoxTimerTask extends AbstractSystemTimerTask<SystemTimerTaskData>{

	public AllianceBoxTimerTask(SystemTimerCommand timerTaskCommand) {
		super(timerTaskCommand);
	}
	
	@Override
	public boolean onExecute(SystemTimerTaskData data) {
		AllianceBoxTimerTaskData allianceBoxTimerTaskData = (AllianceBoxTimerTaskData)data.getParam();
		
		return true;
	}

	@Override
	public SystemTimerTaskData getSystemTimerTaskData(Object... params) {
		SystemTimerTaskData systemTimerTaskData = new SystemTimerTaskData();
		AllianceBoxTimerTaskData allianceBoxTimerTaskData = new AllianceBoxTimerTaskData();
		allianceBoxTimerTaskData.setAllianceId((long)params[0]);
		allianceBoxTimerTaskData.setBoxId((long)params[1]);
		systemTimerTaskData.setParam(allianceBoxTimerTaskData);
		return systemTimerTaskData;
	}
}
