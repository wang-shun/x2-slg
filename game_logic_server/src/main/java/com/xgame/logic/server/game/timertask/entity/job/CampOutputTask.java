package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.CampTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 军营生产
 * @author jacky.jiang
 *
 */
public class CampOutputTask extends AbstractTimerTask{

	public CampOutputTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		CampTimerTaskData campTimerTaskData = new CampTimerTaskData();
//		timerTaskData.setParam(campTimerTaskData);
//		campTimerTaskData.setCampId((int)params[0]);
//		campTimerTaskData.setSoldierId((int)params[1]);
//		campTimerTaskData.setBuildUid((int)params[2]);
//		campTimerTaskData.setNum((int)params[3]);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		CampTimerTaskData campTimerTaskData = (CampTimerTaskData)data.getParam();
		player.getArmyShopManager().outputSuccess(player, campTimerTaskData.getBuildUid(), campTimerTaskData.getSoldierId(), campTimerTaskData.getNum());
		return true;
	}

	@Override
	public boolean cancelTask(Player player, TimerTaskData data) {
		if (data != null) {
			CampTimerTaskData campTimerTaskData = (CampTimerTaskData)data.getParam();
			player.getArmyShopManager().cancelOutput(player, player.roleInfo().getSoldierData().getSoldiers().get(campTimerTaskData.getSoldierId()), campTimerTaskData.getNum(), 0.5f);
		}
		return super.cancelTask(player, data);
	}
}
