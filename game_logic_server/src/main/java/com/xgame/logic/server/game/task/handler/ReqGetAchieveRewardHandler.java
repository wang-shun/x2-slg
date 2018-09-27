package com.xgame.logic.server.game.task.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.task.TaskManager;
import com.xgame.logic.server.game.task.message.ReqGetAchieveRewardMessage;

/** 
 * 领取勋章任务奖励
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetAchieveRewardHandler extends PlayerCommand<ReqGetAchieveRewardMessage>{

	@Autowired
	private TaskManager taskManager;
	
	@Override
    public void action() {
		taskManager.getAchieveReward(player,message.taskId);
    }
}
