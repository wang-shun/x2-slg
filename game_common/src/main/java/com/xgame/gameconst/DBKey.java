package com.xgame.gameconst;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class DBKey {

	public static final String SEQUANCE_ITEM = "ITEM_ID";

	public static final String SEQUANCE_TIMER_TASK = "TIMER_TASK_ID";

	public static final String SEQUANCE_ID = "SEQ_ID";
	
	public static final String SEQUENCE_EQUIPMENT = "EQUIPMENT_ID";
	
	public static final String SEQUENCE_MARCH = "MARCH_ID";
	
	public static final String SEQUENCE_ALLIANCEHELP = "ALLIANCEHELP_ID";
	public static final String SEQUENCE_ALLIANCE_BATTLE_TEAM= "ALLIANCE_BATTLE_TEAM_ID";
	
	public static final String SEQUANCE_DESIGN_MAP = "SEQUANCE_DESIGN_MAP";

	//玩家id自增序列
	public static final String ROLE_ID_KEY = "ROLE_ID_KEY";
	// 聊天房间存储key
	public static final String CHAT_ROOM_KEY = "CHAT_ROOM_KEY";
	// 联盟idkey
	public static final String ALLIANCE_ID_KEY = "ALLIANCE_ID_KEY";
	// 援助idkey
	public static final String ALLIANCE_HELP_ID_KEY = "ALLIANCE_HELP_ID_KEY";

	// 全局入库缓存
	public static final String GLOBAL_KEY = "GLOBAL_KEY";
	
	// 玩家房间信息
	public static final String PLAYER_ROOM_LIST = "PLAYER_ROOM_LIST";
	// 房间聊天key
	public static final String ROOM_CHAT_MEESAGE = "ROOM_CHAT_MEESAGE";
	// 私聊列表
	public static final String PRIVATE_CHAT_KEY = "PRIVATE_CHAT_KEY";
	// 军团聊天key
	public static final String ALLIANCE_CHAT_MESSAGE = "ALLIANCE_CHAT_MESSAGE";

	public static final String SEQUANCE_TASK = "TASK_ID";
	//邮件key
	public static final String MAIL_ID_KEY = "MAIL_ID_KEY";
	public static final String USER_MAIL_ID_KEY = "USER_MAIL_ID_KEY";
	
	//联盟建筑
	public static final String ALLIANCE_BUILD_KEY = "ALLIANCE_BUILD_KEY";
	//事件id
	public static final String EVENT_ID_KEY = "EVENT_ID_KEY";
	//全服排行key
	public static final String GLOBAL_RANK_KEY = "GLOBAL_RANK_KEY";
	// 图纸key
	public static final String DESIGN_MAP_KEY = "DESIGN_MAP_KEY";
	
	//排行榜大小
	public static final int GLOBAL_EVENT_RANK_SIZE = 100;
	public static final int CURRENT_EVENT_RANK_SIZE=100;
	
	// id前缀
	public static final int ID_PRIFIX = 1000;
	// 自增id长度
	public static final int ID_INCR_LENGTH = 10;
	// 自增id前缀长度
	public static final int ID_PRIFIX_LENGTH = 4;
	
	/**
	 * 获取玩家key列表
	 * @return
	 */
	public static String getPatternKey(Class<?> clazz) {
		return StringUtils.join(clazz.getSimpleName(), ":*");
	}
	
	/**
	 * 生成入库dbkey
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static String getDBKey(Class<?> clazz, Serializable id) {
		return StringUtils.join(clazz.getSimpleName(), ":", id);
	}
	
	/**
	 * 获取锁
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static String getLockKey(Class<?> clazz, Serializable id) {
		return String.format("%s:%s:%s", "LOCK", clazz.getSimpleName(), id);
	}
	
	/**
	 * 获取双方玩家组合key
	 * @param sendPlayerId
	 * @param receivePlayerId
	 * @return
	 */
	public static String getCombineKey(long sendPlayerId, long receivePlayerId) {
		if(sendPlayerId > receivePlayerId) {
			return String.format("%s#%s", receivePlayerId, sendPlayerId);
		} else {
			return String.format("%s#%s", sendPlayerId, receivePlayerId);
		}
	}
	
	/**
	 * 生成key
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static String getRoomMessageKey(long roomId) {
		return String.format("%s:%s", ROOM_CHAT_MEESAGE, roomId);
	}
	
	/**
	 * 获取玩家房间列表
	 * @param playerId
	 * @return
	 */
	public static String getPlayerRoomKey(long playerId) {
		return String.format("%s:%s", PLAYER_ROOM_LIST, playerId);
	}
	
	/**
	 * 聊天室key
	 * @param clazz
	 * @param roomName
	 * @param serverId
	 * @return
	 */
	public static String getChatRoomKey(Class<?>clazz, int serverId, String roomName) {
		return String.format("%s:%s:%s", clazz.getSimpleName(), serverId, roomName);
	}
	
	
	/**
	 * 聊天室key
	 * @param clazz
	 * @param roomName
	 * @param serverId
	 * @return
	 */
	public static String getChatRoomPattern(Class<?>clazz, int serverId, String roomNamePattern) {
		return String.format("%s:%s:%s*", clazz.getSimpleName(), serverId, roomNamePattern);
	}
	
//	public static String getPrivateMessageKey(long senderPlayerId, long targetPlayerId) {
//		return String.format("%s:%s", PRIVATE_CHAT_KEY, getCombineKey(senderPlayerId, targetPlayerId));
//	}
	
	public static String getAllianceHelpId(long allianceId, long playerId) {
		return String.format("%s:%s", allianceId, playerId);
	}
	
	/**
	 * 生成联盟队伍id
	 * @param allianceId
	 * @param playerId
	 * @return
	 */
	public static String getAllianceBattleTeamId(long allianceId, long teamId) {
		return String.format("%s:%s", allianceId, teamId);
	}
}

