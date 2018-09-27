package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.ModTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 修理厂队列
 * @author jacky.jiang
 *
 */
public class ModFactoryTask extends AbstractTimerTask {

	public ModFactoryTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}


	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		ModTimerTaskData modTaskData = (ModTimerTaskData)data.getParam();
		player.getRepairManager().modSuccess(player, modTaskData.getSoldierList());
		return true;
	}
}
