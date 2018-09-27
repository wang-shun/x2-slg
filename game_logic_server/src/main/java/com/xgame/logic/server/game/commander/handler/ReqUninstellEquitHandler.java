package com.xgame.logic.server.game.commander.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.commander.message.ReqUninstellEquitMessage;
import com.xgame.logic.server.game.commander.message.ResUninstellEquitMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUninstellEquitHandler extends PlayerCallbackCommand<ReqUninstellEquitMessage>{


    @Override
    public ResUninstellEquitMessage callback() {
        return player.getCommanderManager().uninsertEquit(message.equitId);
    }
}
