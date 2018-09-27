package com.xgame.logic.server.game.chat.constant;


/**
 * 
 * @author jacky.jiang
 *
 */
public class ChatConstant {
	
	/**
	 * 离线消息保存时间
	 */
	public static final int OFFLINE_CHAT_MESSAGE_TIME = 7 * 24 * 60 * 60 * 1000;
	
	/**
	 * 世界聊天缓存的数量
	 */
	public static final int WORLD_CHAT_CACHE_NUM = 40;
	
	/**
	 * 军团聊天记录保留数
	 */
	public static final int ALLIANCE_CHAT_CACHE_NUM = 40;
	
	/**
	 * 聊天内容大小
	 */
	public static final int CHAT_CONTENT_SIZE = 300;
	
	/**
	 * 玩家私聊聊天记录 
	 */
	public static final int OFFLINE_MESSAGE_SIZE = 1000;
	
	/**
	 * 聊天室记录数量
	 */
	public static final int CHAT_ROOM_MESSAGE_SIZE = 40;
	
	/**
	 * 单个玩家缓存200条记录
	 */
	public static final int PRIVATE_CHAT_MESSAGE_SIZE = 200;
	
	/**
	 * 房间创建11创建1自动加入2加入申请3处理申请通过4处理申请拒绝5退出6解散7踢出8编辑9添加禁入10移除禁入
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_CREATE = 11;
	/**
	 * 自动加入
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_AUTO_IN = 1;
	/**
	 * 加入申请
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_APPLY_ADD = 2;
	/**
	 * 处理申请通过
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_APPLY_IN = 3;
	/**
	 * 处理申请拒绝
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_APPLY_REFUSE = 4;
	/**
	 * 退出
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_EXIT = 5;
	/**
	 * 解散
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_DIMISS = 6;
	/**
	 * 踢出
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_KICK = 7;
	/**
	 * 编辑
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_EDIT = 8;
	/**
	 * 添加禁入
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_ADD_BAN = 9;
	/**
	 * 移除禁入
	 */
	public static final int CHAT_ROOM_MESSAGE_TYPE_REMOVE_BAN = 10;
	
}
