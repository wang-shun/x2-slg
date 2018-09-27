package com.xgame.logic.server.game.chat;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.game.chat.constant.ChatChannelType;
import com.xgame.logic.server.game.chat.constant.ChatConstant;
import com.xgame.logic.server.game.chat.converter.ChatConverter;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 聊天公共管理器
 * @author jacky.jiang
 *
 */
@Component
public class WorldChatManager {
	
	private Queue<ChatMessage> worldChatMessage = new ConcurrentLinkedQueue<ChatMessage>();
	
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private GlobalRedisClient globalRedisClient;
	
	// 发送公告时间
	public static final int SEND_PER_TIME = 5 * 1000;
	
	/**
	 * 发送世界聊天
	 * @param player
	 * @param content
	 */
	public void sendWorldChat(Player player, String content, int messageType, String source) {
		// TODO 聊天消息最大60条
		ChatMessage chatMessage = new ChatMessage();
		GlobalPir config = GlobalPirFactory.get(GlobalConstant.CHAT_PLAYER_LEVEL_MIN);
		int levelLimit = Integer.valueOf(config.getValue());
		
		// 主基地等级
		int mainBuildLevel = player.getCountryManager().getMainBuildControl().getDefianlBuild().getLevel();
		if(levelLimit > mainBuildLevel) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE4.get(), String.valueOf(levelLimit));
			return;
		}
		
		// 简单的单独加锁
		synchronized (worldChatMessage) {
			int time  = Integer.valueOf(GlobalPirFactory.get(GlobalConstant.CHAT_SEND_LIMIT_TIME).getValue());
			if(player.getSendWorldTime() +  time > System.currentTimeMillis()) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE5.get());
				return;
			}
			
			if(worldChatMessage.size() >= ChatConstant.WORLD_CHAT_CACHE_NUM) {
				worldChatMessage.poll();
			} 
			
			chatMessage.setContent(content);
			chatMessage.setSendPlayerId(player.getRoleId());
			chatMessage.setSendTime(System.currentTimeMillis());
			chatMessage.setChannel(ChatChannelType.WORLD.getType());
			chatMessage.setMessageType(messageType);
			chatMessage.setSource(source);
			worldChatMessage.offer(chatMessage);
			
			player.setSendWorldTime(System.currentTimeMillis());
		}
		
		// 广播消息
		ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
		receiveChatMessage.chatMessageInfo = ChatConverter.converterChatMessageInfo(chatMessage, player);
		sessionManager.broadcast(receiveChatMessage);
	}
	
	public List<ChatMessage> getWorldChatMessage() {
		List<ChatMessage> chatList = Lists.newArrayList();
		chatList.addAll(worldChatMessage);
		return chatList;
	}

	public void setWorldChatMessage(Queue<ChatMessage> worldChatMessage) {
		this.worldChatMessage = worldChatMessage;
	}
}
