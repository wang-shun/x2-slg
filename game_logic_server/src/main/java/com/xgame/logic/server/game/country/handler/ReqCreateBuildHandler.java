package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.message.ReqCreateBuildMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCreateBuildHandler extends PlayerCommand<ReqCreateBuildMessage>{
    @Override
    public void action() {
        player.getCountryManager().reqCreateBuild(message.sid, message.uid, message.x, message.y, message.createType);
    }
}
