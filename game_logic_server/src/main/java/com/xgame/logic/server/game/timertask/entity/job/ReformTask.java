package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.ReformTimeTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 改装任务
 * @author jacky.jiang
 *
 */
public class ReformTask extends AbstractTimerTask {

	public ReformTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		ReformTimeTaskData reformTimeTaskData = new ReformTimeTaskData();
//		timerTaskData.setParam(reformTimeTaskData);
//		reformTimeTaskData.setId((long)params[0]);
//		reformTimeTaskData.setNum((int)params[1]);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		ReformTimeTaskData reformTaskData = (ReformTimeTaskData)data.getParam();
		player.getModifyManager().refittingSucc(player, reformTaskData.getSoldierId(),reformTaskData.getNum());
		return true;
	}
	
}
