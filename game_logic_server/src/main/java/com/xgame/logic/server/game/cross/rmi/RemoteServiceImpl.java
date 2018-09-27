package com.xgame.logic.server.game.cross.rmi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.PrivateChatManager;
import com.xgame.logic.server.game.chat.converter.ChatConverter;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.CrossWorldInfo;
import com.xgame.logic.server.game.friend.ReleationShipManager;
import com.xgame.logic.server.game.player.constant.PlayerState;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.msglib.ResMessage;

/**
 * 跨服请求接口
 * @author jacky.jiang
 *
 */
@Component
public class RemoteServiceImpl implements RemoteService {

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private ChatRoomManager chatRoomManager;
	@Autowired
	private ReleationShipManager releationShipManager;
	@Autowired
	private PrivateChatManager privateChatManager;
	@Autowired
	private WorldLogicManager mapLogicManager;
	
	@Override
	public void sayHello() {
		System.out.println("aaaaaaaaaaaaaaaaaaaaa");
	}

	@Override
	public void pushMessage(long playerId, ResMessage resMessage) {
		PlayerSession playerSession = sessionManager.getSessionByPlayerId(playerId);
		if(playerSession != null) {
			playerSession.send(resMessage);
		}
	}

	@Override
	public void applyAddRoom(CrossPlayer crossPlayer, String roomKey) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.applyAddRoom(crossPlayer, roomKey);
				}
			});
		}
	}

	@Override
	public void dealApply(CrossPlayer crossPlayer, long roomId, long targetPlayerId, boolean result) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.dealApply(crossPlayer, roomId, targetPlayerId, result);
				}
			});
		}
	}

	@Override
	public void exitRoom(CrossPlayer crossPlayer, long roomId) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.exitRoom(crossPlayer, roomId);
				}
			});
		}
	}

	@Override
	public void kickPlayer(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.kickPlayer(crossPlayer, roomId, targetPlayerId);
				}
			});
		}
	}

	@Override
	public void addBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.addBanPlayerId(crossPlayer, roomId, targetPlayerId);
				}
			});
		}
	}

	@Override
	public void removeBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.removeBanPlayerId(crossPlayer, roomId, targetPlayerId);
				}
			});
		}
	}

	@Override
	public void editRoomInfo(CrossPlayer crossPlayer, long roomId, String roomName, String desc, boolean password) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, crossPlayer.getId());
		if(player != null) {
			player.async(new Runnable() {
				@Override
				public void run() {
					chatRoomManager.editRoomInfo(crossPlayer, roomId, roomName, desc, password);
				}
			});
		}
	}

	@Override
	public void pushPrivateMessage(long playerId, long targetPlayerId, ChatMessage chatMessage) {
		Player targetSessionPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		CrossPlayer crossPlayer = InjectorUtil.getInjector().crossPlayerManager.getCrossPlayer(playerId);
		CrossPlayer targetPlayer = InjectorUtil.getInjector().crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(targetSessionPlayer != null && targetSessionPlayer.getState() != PlayerState.OFFLINE) {
			// 对方在线直接推送消息
			ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
			receiveChatMessage.chatMessageInfo = ChatConverter.converterPrivateChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo(), targetPlayer.getSimpleRoleInfo());
			targetSessionPlayer.send(receiveChatMessage);
		} else {
			// 不在线加入离线消息列表
			releationShipManager.addChatList(targetPlayerId, playerId);
			privateChatManager.savePrivateChat(playerId, targetPlayerId, chatMessage);
		}
	}

	@Override
	public CrossWorldInfo getWorldInfo(int x, int y) {
		CrossWorldInfo crossWorldInfo = mapLogicManager.getRangeSpriteInfo(x, y);
		crossWorldInfo.setVectorInfos(mapLogicManager.getWorldMarch(x, y));
		return crossWorldInfo;
	}

	@Override
	public SpriteBean getSpriteInfo(int x, int y) {
		return mapLogicManager.getSpriteInfo(x, y);
	}
	
}
