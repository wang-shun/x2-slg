package com.xgame.logic.server.game.commander.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.commander.message.ReqChangeNameMessage;
import com.xgame.logic.server.game.commander.message.ResChangeNameMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqChangeNameHandler extends PlayerCallbackCommand<ReqChangeNameMessage>{


    @Override
    public ResChangeNameMessage callback() {
        return player.getCommanderManager().changeName(message.utf);
    }
}
