package com.xgame.logic.server.game.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.message.ReqCreateChatRoomMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCreateChatRoomHandler extends PlayerCommand<ReqCreateChatRoomMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;
	
	@Override
    public void action() {
		chatRoomManager.createRoom(player, message.roomName, message.roomDesc, message.open);
    }
}
