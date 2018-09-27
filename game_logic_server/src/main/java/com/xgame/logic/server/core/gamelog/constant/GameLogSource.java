package com.xgame.logic.server.core.gamelog.constant;

/**
 * 游戏当中产生日志的来源
 * @author jacky.jiang
 *
 */
public enum GameLogSource {

	DEFAULT,
	
	/**
	 * 使用固定宝箱
	 */
	USE_FIX_BOX,
	
	/**
	 * 使用随机宝箱
	 */
	USE_RANDOM_BOX,
	
	/**
	 * 使用道具
	 */
	USE_ITEM,
	
	/**
	 *  工业工厂产出
	 */
	INDUSTRY_OUTPUT,
	
	/**
	 * 科技升级奖励
	 */
	TECH_LEVEL_UP,
	
	/**
	 * 取消科技升级
	 */
	CANCEL_TECH_LEVEL_UP,
	
	/**
	 * 建筑升级奖励
	 */
	BUILD_LEVEL_UP,
	
	/**
	 * 取消建筑升级
	 */
	CANCEL_BUILD_LEVEL_UP,
	
	/**
	 * 移除建筑奖励
	 */
	REMOVE_BUILD,
	
	/**
	 * 购买材料
	 */
	BUY_FRAGMENT,
	
	/**
	 * 合成材料
	 */
	COMPOSE_FRAGMENT,
	
	/**
	 * 装备锻造
	 */
	FORGING_EQUIPMENT,
	
	/**
	 * 合成装备
	 */
	COMPOSE_EQUIPMENT,
	
	/**
	 * 分解装备
	 */
	DECOMPOSE_EQUIPMENT,
	
	/**
	 * 购买万能植入体材料
	 */
	
	BUY_SPECIAL_EQUIPMENT,
	
	/**
	 * 商城购买
	 */
	SHOP_BUY,
	
	/**
	 * GM
	 */
	GM,
	
	/**
	 * 改名
	 */
	CHANAGE_NAME,
	
	/**
	 * 改变形象
	 */
	CHANGE_STYLE,
	
	/**
	 * 使用统帅道具
	 */
	USE_CAPTAIN,
	
	/**
	 * 增加统帅点
	 */
	ADD_CAPTAIN_POINT,
	
	/**
	 * 切换天赋页
	 */
	SWITCH_TALENT_PAGE,
	
	/**
	 * 材料产出
	 */
	FRAGMENT_OUTPUT,
	
	/**
	 * 创建联盟
	 */
	CREATE_ALLILANCE,
	
	/**
	 * 联盟
	 */
	ALLIANCE_JOIN,
	
	/**
	 * 联盟捐献
	 */
	ALLIANCE_DONATE,
	
	/**
	 * 编辑联盟信息
	 */
	EDIT_ALLIANCE_INFO,
	
	/**
	 * 生产坦克
	 */
	CAMP_OUTPUT_TANK,
	
	/**
	 * 生产suv
	 */
	CAMP_OUTPUT_SUV,
	
	/**
	 * 生产飞机
	 */
	CAMP_OUTPUT_PLANE,
	
	
	/**
	 * 取消生产坦克
	 */
	CANCEL_CAMP_OUTPUT_TANK,
	
	/**
	 * 取消生产suv
	 */
	CANCEL_CAMP_OUTPUT_SUV,
	
	/**
	 * 取消生产飞机
	 */
	CANCEL_CAMP_OUTPUT_PLANE,
	
	/**
	 * 解锁工业工厂生产槽位
	 */
	UNLOCK_INDUSTRY_POSITION,
	
	/**
	 * 采矿场产出
	 */
	MINE_OUTPUT,
	
	/**
	 * 修咖士兵
	 */
	MODIFY_SOLDIER,
	
	/**
	 * 销毁士兵
	 */
	DESTORY_SOLDIER,
	
	/**
	 * 改造士兵
	 */
	REFIT_SOLDIER,
	
	/**
	 * 联盟交易
	 */
	ALLIANCE_TRADE,
	
	/**
	 * 加速时间队列
	 */
	SPEED_UP_TIMER_TASK,
	
	/**
	 * 家园战斗
	 */
	COUNTRY_WAR,
	
	/**
	 * 出征
	 */
	MARCH,
	
	/**
	 * 采集
	 */
	EXPLORER,
	
	/**
	 * 重置时间刷新任务
	 */
	RESET_TIME_REFRESH_TASK,
	
	/**
	 * 事件发奖
	 */
	EVENT_ACHIVE,
	
	/**
	 * 副本战斗
	 */
	COPY,
	
	/**
	 * 恐怖分子
	 */
	TERRORIST,
	
	/**
	 * 基地任务奖励
	 */
	BASE_TASK,
	
	/**
	 * 勋章任务
	 */
	ACHIEVE_TASK,
	
	/**
	 * 活跃度任务奖励
	 */
	ACTIVE_TASK,
	
	/**
	 * 时间刷新任务奖励
	 */
	REFRESH_TASK,
	
	/**
	 * 碎片合成
	 */
	FRAGMENT_COMPOSE,
	
	/**
	 * 使用配件宝箱
	 */
	USE_PEIJIAN_BOX,
	
	/**
	 * 副本宝箱
	 */
	COPY_BOX,
	
	/**
	 * 迁城
	 */
	MOVE_CITY,
	
	/**
	 * 快速购买
	 */
	FAST_PAID,
	
	/**
	 * 聊天
	 */
	CHAT,
	
	/**
	 * 科技捐献
	 */
	SCIENCE_DONATE,
	
	/**
	 * 军团活跃资源
	 */
	ALLIANCE_ACTIVITY_RESOURCE,
	
	/**
	 * 军团商店
	 */
	ALLIANCE_SHOP,
	
	/**
	 * 军团宝箱
	 */
	USE_ARMY_BOX,
	
	/**
	 * 军团战事奖励
	 */
	ARMY_FIGHT_REWARD,
	
	/**
	 * 插入装备
	 */
	INSERT_EQUIT,
	
	/**
	 * 卸载装备
	 */
	UNINSERT_EQUIT,
	
	/**
	 * 天赋升级
	 */
	COMMANDER_LEVEL_UP,
	
	/**
	 * 重置天赋
	 */
	RESET_TANLENT,
	
	/**
	 * 设置出兵
	 */
	SETTING_SOLDIER,
	
	/**
	 * 下掉士兵
	 */
	SHUTDOWN_SOLDIER,
	
	;
}
