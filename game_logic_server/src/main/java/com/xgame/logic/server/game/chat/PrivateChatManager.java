package com.xgame.logic.server.game.chat;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.logic.server.game.chat.bean.ChatPlayerMessageInfo;
import com.xgame.logic.server.game.chat.constant.ChatChannelType;
import com.xgame.logic.server.game.chat.constant.ChatConstant;
import com.xgame.logic.server.game.chat.converter.ChatConverter;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.message.ResLoginChatMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.constant.CrossConstant;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.cross.rmi.RemoteService;
import com.xgame.logic.server.game.friend.ReleationShipManager;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 玩家聊天
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrivateChatManager extends AbstractComponent<Player> {
	
	@Autowired
	private WorldChatManager chatManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private ChatRoomManager chatRoomManager;
	@Autowired
	private ReleationShipManager releationShipManager;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private AllianceChatManager allianceChatManager;
	
	/**
	 * 处理登陆
	 * @param player
	 */
	@Override
	public void loginLoad(Object ... param) {
		Player player = this.getPlayer();
		ResLoginChatMessage resLoginChatMessage =  new ResLoginChatMessage();
		for(ChatMessage chatMessage : chatManager.getWorldChatMessage()) {
			CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(chatMessage.getSendPlayerId());
			if(crossPlayer != null) {
				resLoginChatMessage.worldMessageList.add(ChatConverter.converterChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo()));
			}
		}	
		
		// 返回私聊离线消息
		resLoginChatMessage.chatPlayerMessageList = getPrivateMessage(player);
		
		//返回军团历史消息
		if(player.getAllianceId() > 0){
			List<ChatMessage> chatMessageList = allianceChatManager.getAllianceChatMessage(player.getAllianceId());
			for(ChatMessage chatMessage : chatMessageList){
				Player crossPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, chatMessage.getSendPlayerId());
				resLoginChatMessage.allianceMessageList.add(ChatConverter.converterChatMessageInfo(chatMessage, crossPlayer));
			}
		}
		
		// 聊天室消息数量
		player.send(resLoginChatMessage);
	}

	/**
	 * 发送私聊
	 * @param player
	 * @param targetPlayer
	 * @param content
	 * @param channel
	 */
	public void sendPrivateChat(Player player, CrossPlayer targetPlayer, String content, int channel, String source, int messageType) {
		
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setContent(content);
		chatMessage.setSendPlayerId(player.getId());
		chatMessage.setTarget(String.valueOf(targetPlayer.getId()));
		chatMessage.setSendTime(System.currentTimeMillis());
		chatMessage.setChannel(ChatChannelType.PRIVATENESS.getType());
		chatMessage.setSource(source);
		chatMessage.setMessageType(messageType);
		
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
		if(crossPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4);
			log.error("玩家数据不存在。");
			return;
		}
		
		if(player.getServer() == targetPlayer.getServerId()) {//同服消息
			PlayerSession targetSessionPlayer = sessionManager.getSessionByPlayerId(targetPlayer.getId());
			ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
			receiveChatMessage.chatMessageInfo = ChatConverter.converterPrivateChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo(),targetPlayer.getSimpleRoleInfo());
			player.send(receiveChatMessage);
			 
			if (targetSessionPlayer != null) {
				targetSessionPlayer.send(receiveChatMessage);
			} else {
				// 不在线加入离线消息列表
				releationShipManager.addChatList(player.getId(), targetPlayer.getId());
				releationShipManager.addChatList(targetPlayer.getId(), player.getId());
				savePrivateChat(player.getId(), targetPlayer.getId(), chatMessage);
			}
		} else {//跨服消息
			ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
			receiveChatMessage.chatMessageInfo = ChatConverter.converterPrivateChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo(),targetPlayer.getSimpleRoleInfo());
			player.send(receiveChatMessage);
			
			RemoteService remoteService = InjectorUtil.getInjector().getBean(CrossConstant.CROSS_SERVICE_NAME + targetPlayer.getServerId());
			remoteService.pushPrivateMessage(player.getId(), targetPlayer.getId(), chatMessage);
		}
	}
	
	/**
	 * 获取私聊消息列表
	 * @param player
	 * @return
	 */
	private List<ChatPlayerMessageInfo> getPrivateMessage(Player player) {
		List<ChatPlayerMessageInfo> chatMessageInfos = new ArrayList<>();
		List<SimpleRoleInfo> chatSimpleList = releationShipManager.getChatList(player);
		if(chatSimpleList != null) {
			for(SimpleRoleInfo simpleRoleInfo: chatSimpleList) {
				List<ChatMessageInfo> chatMessages = getOfflinePrivateChat(player, simpleRoleInfo);
				
				// 添加消息信息
				ChatPlayerMessageInfo chatPlayerMessageInfo = new ChatPlayerMessageInfo();
				chatPlayerMessageInfo.playerList = SimplePlayerConverter.converterSimplePlayer(simpleRoleInfo);
				chatPlayerMessageInfo.chatMessageList = chatMessages;
				chatMessageInfos.add(chatPlayerMessageInfo);
			}	
		}
		return chatMessageInfos;
	}
	
	/**
	 * 保存私聊
	 * @param playerId
	 * @param targetPlayerId
	 * @param chatMessage
	 */
	public void savePrivateChat(long playerId, long targetPlayerId, ChatMessage chatMessage) {
		List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
		String obj = redisClient.hget(DBKey.PRIVATE_CHAT_KEY, DBKey.getCombineKey(playerId, targetPlayerId));
		if(!StringUtils.isBlank(obj)) {
			synchronized (chatMessages) {
				chatMessages = JsonUtil.parseArray(obj, ChatMessage.class);
				if(chatMessages.size() >= ChatConstant.OFFLINE_MESSAGE_SIZE) {
					chatMessages.remove(0);
				}
				chatMessages.add(chatMessage);
			}
		}  else{
			chatMessages.add(chatMessage);
		}
		
		// 添加私聊信息
		redisClient.hset(DBKey.PRIVATE_CHAT_KEY, DBKey.getCombineKey(playerId, targetPlayerId), JsonUtil.toJSON(chatMessages));
	}
	
	/**
	 * 返回玩家离线消息
	 * @param player
	 * @param targetPlayer
	 * @return
	 */
	private List<ChatMessageInfo> getOfflinePrivateChat(Player player, SimpleRoleInfo targetPlayer) {
		List<ChatMessageInfo> chatMessageInfos = new ArrayList<>();
		String obj = redisClient.hget(DBKey.PRIVATE_CHAT_KEY, DBKey.getCombineKey(player.getId(), targetPlayer.getId()));
		if(!StringUtils.isBlank(obj)) {
			List<ChatMessage> chatMessages = JsonUtil.parseArray(obj, ChatMessage.class);
			if(chatMessages != null && !chatMessages.isEmpty()) {
				for	(ChatMessage chatMessage : chatMessages) {
					if(chatMessage.getSendTime() > player.roleInfo().getBasics().getLogoutTime()) {
						chatMessageInfos.add(ChatConverter.converterChatMessageInfo(chatMessage, targetPlayer));
					}
				}
			}
		}
		return chatMessageInfos;
	}
}
