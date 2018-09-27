package com.xgame.logic.server.game.task.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.task.TaskManager;
import com.xgame.logic.server.game.task.message.ReqGetTimeRefreshTaskRewardMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetTimeRefreshTaskRewardHandler extends PlayerCommand<ReqGetTimeRefreshTaskRewardMessage>{



	@Autowired
	private TaskManager taskManager;
	
	@Override
    public void action() {
		taskManager.getTimeRefreshTaskReward(player, message.type, message.id);
    }
}
