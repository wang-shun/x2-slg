package com.xgame.logic.server.game.timertask.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.message.ReqCancelTimerTaskMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCancelTimerTaskHandler extends PlayerCommand<ReqCancelTimerTaskMessage>{
    @Override
    public void action() {
        TimerTaskData taskData = player.getTimerTaskManager().getTimerTaskData(message.timerUid);
		if(taskData!=null){
			TimerTaskHolder.getTimerTask(taskData.getQueueId()).cancelTask(player, taskData);
		}
    }
}
