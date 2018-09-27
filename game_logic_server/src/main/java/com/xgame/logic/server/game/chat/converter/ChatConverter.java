package com.xgame.logic.server.game.chat.converter;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.logic.server.game.chat.bean.ChatRoomBean;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.entity.GameChatRoom;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 聊天转换器
 * @author jacky.jiang
 *
 */
public class ChatConverter {
	
	
	public static ChatMessageInfo converterChatMessageInfo(ChatMessage chatMessage, SimpleRoleInfo senderPlayer) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.channel = chatMessage.getChannel();
		chatMessageInfo.content = chatMessage.getContent();
		chatMessageInfo.target = chatMessage.getTarget();
		chatMessageInfo.messageType = chatMessage.getMessageType();
		chatMessageInfo.source = chatMessage.getSource();
		chatMessageInfo.sendTime = chatMessage.getSendTime();
		chatMessageInfo.senderInfo = JsonUtil.toJSON(senderPlayer);
		return chatMessageInfo;
	}
	
	
	public static ChatMessageInfo converterChatMessageInfo(ChatMessage chatMessage, Player senderPlayer) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.channel = chatMessage.getChannel();
		chatMessageInfo.content = chatMessage.getContent();
		chatMessageInfo.target = chatMessage.getTarget();
		chatMessageInfo.messageType = chatMessage.getMessageType();
		chatMessageInfo.source = chatMessage.getSource();
		chatMessageInfo.sendTime = chatMessage.getSendTime();
		
		// 返回玩家详细信息
		Alliance alliance = senderPlayer.getAllianceManager().get(senderPlayer.getAllianceId());
		String[] allianceTitle = senderPlayer.getPlayerAllianceManager().getAllianceTitle(senderPlayer.getId());
		//TODO 世界政府头衔
		SimpleRoleInfo simpleRoleInfo = SimplePlayerConverter.converterSimpleRoleInfo(senderPlayer.roleInfo(), alliance,allianceTitle[0],allianceTitle[1],"");
		chatMessageInfo.senderInfo = JsonUtil.toJSON(simpleRoleInfo);
		return chatMessageInfo;
	}
	
	public static ChatRoomBean converterChatRoom(GameChatRoom gameChatRoom) {
		ChatRoomBean chatRoomBean = new ChatRoomBean();
		chatRoomBean.desc = gameChatRoom.getDesc();
		chatRoomBean.name = gameChatRoom.getRoomName();
		chatRoomBean.roomId = gameChatRoom.getRoomId();
		chatRoomBean.roomLeaderId = gameChatRoom.getRoomLeaderId();
		chatRoomBean.serverId = gameChatRoom.getServerId();
		for(long playerId : gameChatRoom.getPlayerIds()) {
			CrossPlayer crossPlayer = InjectorUtil.getInjector().crossPlayerManager.getCrossPlayer(playerId);
			if(crossPlayer != null) {
				chatRoomBean.roomPlayerList.add(crossPlayer.toCrossPlayerJson());	
			}
		}
		chatRoomBean.password = gameChatRoom.isOpen();
		chatRoomBean.roomKey = gameChatRoom.getRoomKey();
		for(long playerId : gameChatRoom.getBanPlayerIds()) {
			CrossPlayer crossPlayer = InjectorUtil.getInjector().crossPlayerManager.getCrossPlayer(playerId);
			if(crossPlayer != null) {
				chatRoomBean.banPlayerIds.add(crossPlayer.toCrossPlayerJson());	
			}
		}
		
		for(long playerId : gameChatRoom.getApplyPlayerIds()) {
			CrossPlayer crossPlayer = InjectorUtil.getInjector().crossPlayerManager.getCrossPlayer(playerId);
			if(crossPlayer != null) {
				chatRoomBean.applyPlayerIds.add(crossPlayer.toCrossPlayerJson());	
			}
		}
		
		return chatRoomBean;
	}
	
	/**
	 * 私聊增加接收方的详细信息
	 * @param chatMessage
	 * @param senderPlayer
	 * @param targetPlayer
	 * @return
	 */
	public static ChatMessageInfo converterPrivateChatMessageInfo(ChatMessage chatMessage, SimpleRoleInfo senderPlayer, SimpleRoleInfo targetPlayer) {
		ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
		chatMessageInfo.channel = chatMessage.getChannel();
		chatMessageInfo.content = chatMessage.getContent();
		chatMessageInfo.target = chatMessage.getTarget();
		chatMessageInfo.messageType = chatMessage.getMessageType();
		chatMessageInfo.source = chatMessage.getSource();
		chatMessageInfo.sendTime = chatMessage.getSendTime();
		chatMessageInfo.senderInfo = JsonUtil.toJSON(senderPlayer);
		chatMessageInfo.targetInfo = JsonUtil.toJSON(targetPlayer);
		return chatMessageInfo;
	}
	
}
