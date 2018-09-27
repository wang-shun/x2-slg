package com.xgame.logic.server.game.awardcenter.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.game.awardcenter.message.ReqGetAwardMessage;
import com.xgame.logic.server.game.awardcenter.message.ResGetAwardMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetAwardHandler extends PlayerCallbackCommand<ReqGetAwardMessage>{


    @Override
    public ResGetAwardMessage callback() {
    	player.getAwardCenterManager().receive(message.index);
    	return new ResGetAwardMessage();
    }
}
