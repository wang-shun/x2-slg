package com.xgame.logic.server.game.friend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.friend.ReleationShipManager;
import com.xgame.logic.server.game.friend.message.ReqAddFriendMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAddFriendHandler extends PlayerCommand<ReqAddFriendMessage>{

	@Autowired
	private ReleationShipManager releationShipManager;
	
	@Override
    public void action() {
		releationShipManager.addFriend(player, message.targetPlayerId);
	}
}
