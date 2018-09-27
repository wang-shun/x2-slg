package com.xgame.logic.server.game.commander.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.commander.message.ReqGetStatisticMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetStatisticHandler extends PlayerCommand<ReqGetStatisticMessage>{

	
	@Override
    public void action() {
		player.getCommanderManager().sendStatistic();
    }
}
