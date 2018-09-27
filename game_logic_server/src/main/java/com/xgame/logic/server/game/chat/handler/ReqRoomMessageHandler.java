package com.xgame.logic.server.game.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.message.ReqRoomMessageMessage;
import com.xgame.logic.server.game.chat.message.ResRoomMessageMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRoomMessageHandler extends PlayerCommand<ReqRoomMessageMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;

	@Override
    public void action() {
		ResRoomMessageMessage resRoomMessageMessage = new ResRoomMessageMessage();
		resRoomMessageMessage.chatRoomMessageList = chatRoomManager.getRoomMessageList(player);
		player.send(resRoomMessageMessage);
	}
}
