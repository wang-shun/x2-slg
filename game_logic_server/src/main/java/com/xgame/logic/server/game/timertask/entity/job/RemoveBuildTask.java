package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;



/**
 * 移除建筑
 * @author jacky.jiang
 *
 */
public class RemoveBuildTask extends AbstractTimerTask{

	public RemoveBuildTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData stateTaskData = new TimerTaskData();
//		BuildTimerTaskData buildTimerTaskData = new BuildTimerTaskData();
//		stateTaskData.setParam(buildTimerTaskData);
//		buildTimerTaskData.setSid((int)params[0]);
//		buildTimerTaskData.setUid((int)params[1]);
//		buildTimerTaskData.setState((int)params[2]);
//		return stateTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		BuildTimerTaskData buildTimerTaskData = (BuildTimerTaskData)data.getParam();
		player.getCountryManager().removeSuccess(buildTimerTaskData.getSid(), buildTimerTaskData.getBuildingUid());
		InjectorUtil.getInjector().dbCacheService.update(player);
		return true;
	}
}
