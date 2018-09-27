package com.xgame.framework.lifecycle;

//启动顺序
public enum StartupOrder {
	ATTRIBUTE_MANAGER,//属性
	DBCACHE_START, // dbcache启动
	WORLD_START, // 世界地图启动
	TIMER_START, // 玩家任务启动
	SCHEDULE_START, // 延迟任务启动
	CHAT_ROOM_START,// 聊天室
	SHOP_START, // 商城
	CHAT_START, // 聊天启动
	SERVERGROUP_START, // 服务器组启动
	ALLIANCE_START, // 联盟启动加载 
	ALLIANCE_BATTLE_TEAM_START, //联盟战斗队伍,
	WAR_START, // 战斗启动初始化
	EMAIL_START, // 邮件启动初始化
	EVENT_START, // 事件启动初始化
	WILD_BATTLE_REPORT_PUSH, // 战斗推送
//	ALLIANCE_TERRITORY, // 领地信息
}
