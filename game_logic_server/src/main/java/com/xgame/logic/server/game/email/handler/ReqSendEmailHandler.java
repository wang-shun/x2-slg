package com.xgame.logic.server.game.email.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.message.ReqSendEmailMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSendEmailHandler extends PlayerCommand<ReqSendEmailMessage>{


	@Override
    public void action() {
		if(message.type == 1) {
			 player.getPlayerMailInfoManager().sendUserEmail(player, message.playerName, message.title, message.content);
		} else if(message.type == 2) {
			player.getPlayerMailInfoManager().sendAllianceEmail(player, message.title, message.content);
		}
    }
}
