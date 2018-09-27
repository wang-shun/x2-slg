package com.xgame.logic.server.game.friend.entity;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

import io.protostuff.Tag;

/**
 * 好友关系列表
 * @author jacky.jiang
 *
 */
public class ReleationShip implements IEntity<Long> {
	
	/**
	 * 玩家id
	 */
	@Tag(1)
	private long playerId;
	
	/**
	 * 好友列表
	 */
	@Tag(2)
	private ConcurrentHashSet<Long> friendList = new ConcurrentHashSet<Long>();
	
	/**
	 * 黑名单列表
	 */
	@Tag(3)
	private ConcurrentHashSet<Long> blackList = new ConcurrentHashSet<>();
	
	/**
	 * 私聊列表
	 */
	@Tag(4)
	private ConcurrentHashSet<Long> chatList = new ConcurrentHashSet<>();
	
	/**
	 * 添加好友
	 * @param targetPlayerId
	 */
	public void addFriend(long targetPlayerId) {
		blackList.remove(targetPlayerId);
		friendList.add(targetPlayerId);
	}
	
	/**
	 * 移除好友
	 * @param targetPlayerId
	 */
	public void removeFriend(long targetPlayerId) {
		friendList.remove(targetPlayerId);
	}
	
	/**
	 * 好友是否存在
	 * @param targetPlayerId
	 * @return
	 */
	public boolean existFriend(long targetPlayerId) {
		return friendList.contains(targetPlayerId);
	}
	
	/**
	 * 添加黑名单
	 * @param targetPlayerId
	 */
	public void addBlackList(long targetPlayerId) {
		friendList.remove(targetPlayerId);
		blackList.add(targetPlayerId);
	}
	
	/**
	 * 删除黑名单
	 * @param targetPlayerId
	 */
	public void deleteBlackList(long targetPlayerId) {
		blackList.remove(targetPlayerId);
	}
	
	/**
	 * 黑名单是否存在
	 * @param targetPlayerId
	 * @return
	 */
	public boolean existBlackList(long targetPlayerId) {
		return blackList.contains(targetPlayerId);
	}
	
	/**
	 * 添加聊天列表
	 * @param targetPlayerId
	 */
	public void addChatList(long targetPlayerId) {
		chatList.add(targetPlayerId);
	}
	
	/**
	 * 删除玩家聊天信息
	 * @param targetPlayerId
	 */
	public void deletePlayerChat(long targetPlayerId) {
		chatList.remove(targetPlayerId);
	}
	
	/**
	 * 判断玩家是否存在
	 * @param targetPlayerId
	 * @return
	 */
	public boolean existPlayerChat(long targetPlayerId) {
		return chatList.contains(targetPlayerId);
	}

	@Override
	public Long getId() {
		return playerId;
	}

	@Override
	public void setId(Long k) {
		this.playerId = k;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public Set<Long> getFriendList() {
		return friendList;
	}

	public ConcurrentHashSet<Long> getBlackList() {
		return blackList;
	}

	public void setBlackList(ConcurrentHashSet<Long> blackList) {
		this.blackList = blackList;
	}

	public ConcurrentHashSet<Long> getChatList() {
		return chatList;
	}

	public void setChatList(ConcurrentHashSet<Long> chatList) {
		this.chatList = chatList;
	}

	public void setFriendList(ConcurrentHashSet<Long> friendList) {
		this.friendList = friendList;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("playerId", playerId);
		jBaseData.put("friendList", JsonUtil.toJSON(friendList));
		jBaseData.put("blackList", JsonUtil.toJSON(blackList));
		jBaseData.put("chatList", JsonUtil.toJSON(chatList));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		String friendListStr = jBaseData.getString("friendList", "");
		if(!StringUtils.isEmpty(friendListStr)) {
			this.friendList.addAll(JsonUtil.fromJSON(friendListStr, ConcurrentHashSet.class));
		}
		
		String blackListStr = jBaseData.getString("blackList", "");
		if(!StringUtils.isEmpty(blackListStr)) {
			this.blackList.addAll(JsonUtil.fromJSON(blackListStr, ConcurrentHashSet.class));
		}
		
		
		String chatListStr = jBaseData.getString("chatList", "");
		if(!StringUtils.isEmpty(chatListStr)) {
			this.chatList.addAll(JsonUtil.fromJSON(chatListStr, ConcurrentHashSet.class));
		}
	}
	
}
