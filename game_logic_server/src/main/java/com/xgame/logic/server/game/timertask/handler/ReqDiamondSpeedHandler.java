package com.xgame.logic.server.game.timertask.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.message.ReqDiamondSpeedMessage;
import com.xgame.utils.TimeUtils;

/**
 * @author messageGenerator
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqDiamondSpeedHandler extends PlayerCommand<ReqDiamondSpeedMessage> {
	@Override
	public void action() {
		TimerTaskData taskData = player.getTimerTaskManager().getTimerTaskData(message.timerUid);
		if (taskData != null) {
			int s = (taskData.getTriggerTime() - TimeUtils.getCurrentTime());
			if (s > 0) {
				double diamond = CurrencyUtil.speedChange(player, Math.ceil((double) s / 60));
				if (CurrencyUtil.verify(player, diamond, CurrencyEnum.DIAMOND)) {
					CurrencyUtil.decrement(player, diamond, CurrencyEnum.DIAMOND, GameLogSource.SPEED_UP_TIMER_TASK);
					player.changeTaskTime(message.timerUid, s);
					CurrencyUtil.send(player);
				}
			}
		}
	}
}
