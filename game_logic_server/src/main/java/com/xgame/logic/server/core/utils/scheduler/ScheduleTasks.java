package com.xgame.logic.server.core.utils.scheduler;

/**
 * 任务触发
 * @author jacky.jiang
 *
 */
public class ScheduleTasks {
	
	/**
	 * 聊天房间信息
	 */
	public static final String CHAT_VIEW_ROOM_CREATE = "0 0 4 * * ?";
	
	/**
	 * 清空玩家过期邮件
	 */
	public static final String CLEAN_EMAILS = "0 0 4 * * ?";
	
	/**
	 * 家园障碍物
	 */
	public static final String COUNTRY_OBSTACLE = "0/15 * * * * ?";
	
	/**
	 * 世界地图精灵刷新
	 */
	public static final String REFRESH_SPRITE = "0 */30 * * * ?";
}
