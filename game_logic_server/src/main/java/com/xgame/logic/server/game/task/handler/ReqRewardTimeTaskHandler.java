package com.xgame.logic.server.game.task.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.task.message.ReqRewardTimeTaskMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRewardTimeTaskHandler extends PlayerCommand<ReqRewardTimeTaskMessage>{


	@Override
    public void action() {
    }
}
