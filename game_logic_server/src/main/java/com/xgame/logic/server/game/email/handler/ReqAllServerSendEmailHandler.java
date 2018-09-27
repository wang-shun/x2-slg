package com.xgame.logic.server.game.email.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.message.ReqAllServerSendEmailMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAllServerSendEmailHandler extends PlayerCommand<ReqAllServerSendEmailMessage>{


	@Override
    public void action() {
		// TODO
//        player.getPlayerMailManager().sendAllServerEmail(message.title, message.content);
    }
}
