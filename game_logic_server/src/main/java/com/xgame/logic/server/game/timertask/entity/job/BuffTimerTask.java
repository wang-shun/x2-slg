package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBuff;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.BuffTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;

/**
 * 玩家buff
 * @author jacky.jiang
 *
 */
public class BuffTimerTask extends AbstractTimerTask{

	public BuffTimerTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		BuffTimerTaskData buffTimerTaskData = (BuffTimerTaskData)data.getParam();
		if(buffTimerTaskData != null) {
			PlayerBuff playerBuff = player.getBuffManager().getPlayerBuff(buffTimerTaskData.getBuffId());
			if(playerBuff != null) {
				player.getBuffManager().playerBuffEnd(player, playerBuff.getBuffId(), buffTimerTaskData.getItemId());
			}
		}
		return super.onExecute(player, data);
	}
}
