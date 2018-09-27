package com.xgame.logic.server.game.chat.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.bean.ChatRoomBean;
import com.xgame.logic.server.game.chat.message.ReqRankChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ResRankChatRoomMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRankChatRoomHandler extends PlayerCommand<ReqRankChatRoomMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;

	@Override
    public void action() {
		List<ChatRoomBean> list = chatRoomManager.getViewRooms();
		ResRankChatRoomMessage resRankChatRoomMessage = new ResRankChatRoomMessage();
		resRankChatRoomMessage.chatRoomList = list;
		player.send(resRankChatRoomMessage);
	}
}
