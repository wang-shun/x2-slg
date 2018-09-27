package com.xgame.logic.server.game.chat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.DBKey;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.logic.server.game.chat.constant.ChatChannelType;
import com.xgame.logic.server.game.chat.converter.ChatConverter;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟聊天管理
 * @author jacky.jiang
 *
 */
@Component
public class AllianceChatManager {
	
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private RedisClient redisClient;
	
	/**
	 * 发送联盟聊天消息
	 * @param player
	 * @param content
	 * @param messageType
	 * @param source
	 */
	public void sendAllianceChat(Player player, String content, int messageType, String source) {
		
		PlayerAlliance playerAlliance = playerAllianceManager.get(player.getId());
		if(playerAlliance.getAllianceId() <= 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return;
		}
		
		int time  = Integer.valueOf(GlobalPirFactory.get(GlobalConstant.CHAT_SEND_LIMIT_TIME).getValue());
		if(player.getSendWorldTime() +  time > System.currentTimeMillis()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE5.get());
			return;
		}
		
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE20);
			return;
		}
		
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setContent(content);
		chatMessage.setSendPlayerId(player.getRoleId());
		chatMessage.setSendTime(System.currentTimeMillis());
		chatMessage.setChannel(ChatChannelType.ALLIANCE.getType());
		chatMessage.setMessageType(messageType);
		chatMessage.setSource(source);
		
		// 添加聊天室消息
		saveAllianceMsg(alliance, chatMessage);
		
		// 发给军团玩家
		ChatMessageInfo chatMessageInfo = ChatConverter.converterChatMessageInfo(chatMessage, player);
		ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
		receiveChatMessage.chatMessageInfo = chatMessageInfo;
		sessionManager.writePlayers(alliance.getAllianceMember(), receiveChatMessage);
		
		player.setSendWorldTime(System.currentTimeMillis());
	}
	
	/**
	 * 保存军团消息
	 * @param alliance
	 * @param chatMessage
	 */
	private void saveAllianceMsg(Alliance alliance, ChatMessage chatMessage) {
		String allianceMsg = redisClient.hget(DBKey.ALLIANCE_CHAT_MESSAGE, alliance.getId());
		synchronized (alliance) {
			if(!StringUtils.isBlank(allianceMsg)) {
				List<ChatMessage> chatMessageList = JsonUtil.parseArray(allianceMsg, ChatMessage.class);
				if(chatMessageList != null && chatMessageList.size() >= 200) {
					chatMessageList.remove(0);
				}
				chatMessageList.add(chatMessage);
				allianceMsg = JsonUtil.toJSON(chatMessageList);
			} else {
				List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
				chatMessageList.add(chatMessage);
				allianceMsg = JsonUtil.toJSON(chatMessageList);
			}
			redisClient.hset(DBKey.ALLIANCE_CHAT_MESSAGE, alliance.getId(), allianceMsg);
		}
	}
	
	/**
	 * 获取军团历史消息
	 * @param allianceId
	 * @return
	 */
	public List<ChatMessage> getAllianceChatMessage(long allianceId) {
		List<ChatMessage> chatMessageList = Lists.newArrayList();
		String allianceMsg = redisClient.hget(DBKey.ALLIANCE_CHAT_MESSAGE, allianceId);
		if(!StringUtils.isBlank(allianceMsg)) {
			chatMessageList.addAll(JsonUtil.parseArray(allianceMsg, ChatMessage.class));
		}
		return chatMessageList;
	}
}
