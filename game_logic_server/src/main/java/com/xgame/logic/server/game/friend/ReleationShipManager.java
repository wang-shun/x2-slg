package com.xgame.logic.server.game.friend;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.friend.entity.ReleationShip;
import com.xgame.logic.server.game.friend.message.ResAddBlackListMessage;
import com.xgame.logic.server.game.friend.message.ResAddFriendMessage;
import com.xgame.logic.server.game.friend.message.ResDeleteBlackListMessage;
import com.xgame.logic.server.game.friend.message.ResDeleteFriendMessage;
import com.xgame.logic.server.game.friend.message.ResGetFriendMessage;
import com.xgame.logic.server.game.friend.message.ResSearchPlayerMessage;
import com.xgame.logic.server.game.friend.repository.ReleationShipRepository;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 好友管理器
 * @author jacky.jiang
 *
 */
@Component
public class ReleationShipManager {
	
	@Autowired
	private CrossPlayerManager simpleRoleInfoManager;
	@Autowired
	private ReleationShipRepository releationShipRepository;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private PlayerManager playerManager;
	
	/**
	 * 初始化玩家数据
	 * @param player
	 */
	public void send(Player player) {
		ResGetFriendMessage resGetFriendMessage = getFriendList(player.getId());
		player.send(resGetFriendMessage);
	}
	
	
	/**
	 * 添加好友
	 * @param player
	 * @param targetPlayerId
	 */
	public void addFriend(Player player, long targetPlayerId) {
		
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(crossPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1006_RELATION.CODE2);
			return;
		}
		
		// 参数错误
		if(player.getId() == targetPlayerId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1006_RELATION.CODE1);
			return;
		}
		
		ReleationShip releationShip = releationShipRepository.getOrCreateReleationShip(player.getId());
		if(releationShip.existFriend(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1006_RELATION.CODE1);
			return;
		}
		
		if(releationShip.existBlackList(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1006_RELATION.CODE4);
			return;
		}
		
		releationShip.addFriend(targetPlayerId);
		releationShipRepository.saveReleationShip(releationShip);
		
		ResAddFriendMessage resAddFriendMessage = new ResAddFriendMessage();
 		resAddFriendMessage.playerInfo = SimplePlayerConverter.converterSimplePlayer(crossPlayer.getSimpleRoleInfo());
		player.send(resAddFriendMessage);
		
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E1006_RELATION.CODE1);
	}
	
	/**
	 * 移除好友
	 * @param player
	 * @param targetPlayerId
	 */
	public void deleleFriend(Player player, long targetPlayerId) {
		
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(crossPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4.get());
			return;
		}
		
		ReleationShip releationShip = releationShipRepository.getOrCreateReleationShip(player.getRoleId());
		if(!releationShip.existFriend(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1006_RELATION.CODE2.get());
			return;
		}
		
		releationShip.removeFriend(targetPlayerId);
		releationShipRepository.saveReleationShip(releationShip);
		
		// 删除成功
		ResDeleteFriendMessage resDeleteFriendMessage = new ResDeleteFriendMessage();
		resDeleteFriendMessage.playerId = targetPlayerId;
		player.send(resDeleteFriendMessage);
	}
	
	/**
	 * 添加黑名单列表
	 * @param player
	 * @param targetPlayerId
	 */
	public void addBlackList(Player player, long targetPlayerId) {
		// 参数错误
		if(player.getId() == targetPlayerId) {
			return;
		}
		
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(crossPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4.get());
			return;
		}
		
		ReleationShip releationShip = releationShipRepository.getOrCreateReleationShip(player.getRoleId());
		if(releationShip.existBlackList(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E1005_FRIEND.CODE2.get());
			return;
		}
		//如果从好友加入黑名单 需要删除好友
		ResAddBlackListMessage resAddBlackListMessage = new ResAddBlackListMessage();
		if(releationShip.existFriend(targetPlayerId)){
			resAddBlackListMessage.friendList = SimplePlayerConverter.converterSimplePlayer(crossPlayer.getSimpleRoleInfo());
		}
		
		releationShip.addBlackList(targetPlayerId);
		releationShipRepository.saveReleationShip(releationShip);
		
		resAddBlackListMessage.blackList = SimplePlayerConverter.converterSimplePlayer(crossPlayer.getSimpleRoleInfo());
		player.send(resAddBlackListMessage);
	}
	
	/**
	 * 添加黑名单列表
	 * @param player
	 * @param targetPlayerId
	 */
	public void removeBlackList(Player player, long targetPlayerId) {
		// 参数错误
		if(player.getId() == targetPlayerId) {
			return;
		}
		
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(crossPlayer == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4.get());
			return;
		}
		
		ReleationShip releationShip = releationShipRepository.getOrCreateReleationShip(player.getRoleId());
		if(!releationShip.existBlackList(targetPlayerId)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE4.get());
			return;
		}
		
		// 添加黑名单
		releationShip.deleteBlackList(targetPlayerId);
		releationShipRepository.saveReleationShip(releationShip);
		
		// 返回删除黑名单
		ResDeleteBlackListMessage resAddBlackListMessage = new ResDeleteBlackListMessage();
		resAddBlackListMessage.playerId = targetPlayerId;
		player.send(resAddBlackListMessage);
	}
	
	/**
	 * 返回好友列表
	 * @param player
	 * @return
	 */
	public ResGetFriendMessage getFriendList(long playerId) {
		ResGetFriendMessage resGetFriendMessage = new ResGetFriendMessage();
		ReleationShip releationShip = releationShipRepository.getReleationShip(playerId);
		if(releationShip != null) {
			// 好友列表
			List<SimpleRoleInfo> list = simpleRoleInfoManager.getSimpleRoleInfos(releationShip.getFriendList());
			if(list != null && !list.isEmpty()) {
				for (SimpleRoleInfo simpleRoleInfo : list) {
					resGetFriendMessage.friendList.add(SimplePlayerConverter.converterSimplePlayer(simpleRoleInfo));
				}
			}
			
			// 黑名单列表
			List<SimpleRoleInfo> blackList = simpleRoleInfoManager.getSimpleRoleInfos(releationShip.getBlackList());
			if(blackList != null && !blackList.isEmpty()) {
				for (SimpleRoleInfo simpleRoleInfo : blackList) {
					resGetFriendMessage.blackList.add(SimplePlayerConverter.converterSimplePlayer(simpleRoleInfo));
				}
			}
		}
		
		return resGetFriendMessage;
	}
	
	/**
	 * 获取私聊列表
	 * @param player
	 * @return
	 */
	public List<SimpleRoleInfo> getChatList(Player player) {
		ReleationShip releationShip = releationShipRepository.getReleationShip(player.getRoleId());
		if(releationShip != null) {
			Set<Long> set = releationShip.getChatList();
			if(set != null) {
				return simpleRoleInfoManager.getSimpleRoleInfos(set);
			}
		}
		return null;
	}
	
	/**
	 * 添加私聊列表
	 * @param player
	 * @param targetPlayerId
	 */
	public void addChatList(long playerId, long targetPlayerId) {
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(playerId);
		if(crossPlayer == null) {
			return;
		}
		
		CrossPlayer targetPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(targetPlayer == null) {
			return;
		}
			
		ReleationShip releationShip = releationShipRepository.getOrCreateReleationShip(playerId);
		if(!releationShip.existPlayerChat(targetPlayerId)) {
			releationShip.addChatList(targetPlayerId);
			releationShipRepository.saveReleationShip(releationShip);
		}
	}
	
	/**
	 * 搜索玩家
	 * @param player
	 * @param playerName
	 */
	public void searchPlayer(Player player, String playerName) {
		
		if(StringUtils.isEmpty(playerName) || playerName.length() > 200) {
			return;
		}
		ReleationShip releationShip = releationShipRepository.getReleationShip(player.getId());
		ResSearchPlayerMessage resSearchPlayerMessage = new ResSearchPlayerMessage();
		List<Long> playerIds = playerManager.getPlayerNamePattern(playerName);
		if(playerIds != null && !playerIds.isEmpty()) {
			List<SimpleRoleInfo> simpleRoleInfos = crossPlayerManager.getSimpleRoleInfos(playerIds);
			for(SimpleRoleInfo simpleRoleInfo : simpleRoleInfos) {
				//过滤本人 好友 黑名单
				if(simpleRoleInfo.getId() != player.getId() && (null == releationShip || (!releationShip.existFriend(simpleRoleInfo.getId()) && !releationShip.existBlackList(simpleRoleInfo.getId())))) {
					resSearchPlayerMessage.searchList.add(SimplePlayerConverter.converterSimplePlayer(simpleRoleInfo));
				}
			}
		}
		player.send(resSearchPlayerMessage);
	}
	
	/**
	 * 获取好友列表
	 * @param playerId
	 * @return
	 */
	public Set<Long> getInnerFriendList(long playerId) {
		ReleationShip releationShip = releationShipRepository.getReleationShip(playerId);
		if(releationShip != null) {
			return releationShip.getFriendList();
		}
		return null;
	}
	
}
