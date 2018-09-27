package com.xgame.logic.server.game.timertask.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.VIP.VIPPir;
import com.xgame.config.VIP.VIPPirFactory;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.message.ReqBuildingForFreeMessage;
import com.xgame.utils.TimeUtils;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqBuildingForFreeHandler extends PlayerCommand<ReqBuildingForFreeMessage>{


	@Override
    public void action() {
        Long id = player.roleInfo().getTimerTaskMap().get(message.timerUid);
		if (id != null) {
			//按vip等级秒时间
			TimerTaskData timerTaskData = player.getTimerTaskManager().getTimerTaskData(id);
			if(timerTaskData.getQueueId() != TimerTaskCommand.BUILD.getId() && timerTaskData.getQueueId() != TimerTaskCommand.LEVEL_TECH.getId()){
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE26);
				return;
			}
			
			VIPPir vipPir = VIPPirFactory.get((int)player.roleInfo().getVipInfo().getVipLevel());
			int freeTime2 = Integer.valueOf(vipPir.getComplete());
			long freeTime = TimeUtils.getCurrentTime() + freeTime2 * 60;
			if (freeTime >= timerTaskData.getTriggerTime()) {
				player.changeTaskTime(id, freeTime2 * 60);
				return;
			}
		}
		
		Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE21);
    }
}
