package com.xgame.logic.server.game.country.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.country.message.ReqMoveBuildMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqMoveBuildHandler extends PlayerCommand<ReqMoveBuildMessage>{
    @Override
    public void action() {
        player.getCountryManager().reqMoveBuild(message.uid,message.sid, message.x, message.y);
    }
}
