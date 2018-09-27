package com.xgame.logic.server.game.commander.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.commander.message.ReqResetTalentMessage;
import com.xgame.logic.server.game.commander.message.ResResetTalentMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqResetTalentHandler extends PlayerCallbackCommand<ReqResetTalentMessage>{


    @Override
    public ResResetTalentMessage callback() {
        return player.getCommanderManager().resetTanlent(message.page);
    }
}
