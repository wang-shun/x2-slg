package com.xgame.logic.server.game.commander.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.commander.message.ReqSwitchTanlentMessage;
import com.xgame.logic.server.game.commander.message.ResSwitchTanlentMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSwitchTanlentHandler extends PlayerCallbackCommand<ReqSwitchTanlentMessage>{


    @Override
    public ResSwitchTanlentMessage callback() {
        return player.getCommanderManager().switchTanlent(message.page);
    }
}
