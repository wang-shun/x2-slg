package com.xgame.logic.server.game.chat.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.bean.ChatRoomBean;
import com.xgame.logic.server.game.chat.message.ReqSearchChatRoomMessage;
import com.xgame.logic.server.game.chat.message.ResSearchChatRoomMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSearchChatRoomHandler extends PlayerCommand<ReqSearchChatRoomMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;
	
	@Override
    public void action() {
		List<ChatRoomBean> gameChatRooms = chatRoomManager.serachRoom(message.searchName);
		
		// 返回查询数据
		ResSearchChatRoomMessage resSearchChatRoomMessage = new ResSearchChatRoomMessage();
		resSearchChatRoomMessage.chatRoomList = gameChatRooms;
		
		player.send(resSearchChatRoomMessage);
    }
}
