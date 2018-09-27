package com.xgame.playersearch.util;


/**
 * 事件类型
 * @author jacky.jiang
 *
 */
public class EventTypeConst {

	
	public static final int ALL_EVENT = -1;
	
	// -----------------------------------------------玩家模块---------------------------------------------------------
	/** 加货币 */
	public static final int EVENT_CURRENCY_INCREASE = 1; 
	/** 扣货币 */
	public static final int EVENT_CURRENCY_DECREMENT = 2; 
	/** 创建角色*/
	public static final int EVENT_CREATE_ROLE = 3;
	/** 玩家登录*/
	public static final int EVENT_PLAYER_LOGIN = 4; 
	/** 属性刷新*/
	public static final int EVENT_ATTRIBUTE_REFRESH = 5; 
	/** 刷新战力*/
	public static final int EVENT_REFRESH_FIGHTPOWER = 6; 
	/** 建筑升级成功*/
	public static final int EVENT_BUILD_LEVELUP_FINISH = 7;
	/** 建筑升级开始*/
	public static final int EVENT_BUILD_LEVELUP_START = 8;
	/** 加速*/
	public static final int EVENT_SPEED_UP = 9;
	/** 道具数量变更  */
	public static final int ITEM_CHANGE = 10;
	/** 资源变更  */
	public static final int RESOURCE_CHANGE = 11;
	/** 清理障碍物 */
	public static final int CLEAN_OBSTRUCT = 12;
	/** 创建时间队列  */
	public static final int CREATE_TIMER_TASK = 13;
	/** 结束时间队列  */
	public static final int END_TIMER_TASK = 14;
	/**交易事件 */
	public static final int EVENT_TRADE = 15;
	/**移除障碍物 */
	public static final int REMOVE_OBSTACLE = 16;
	/**解锁配件*/
	public static final int UNLOCK_PEIJIAN = 17;
	/** 士兵数量发生变化  */
	public static final int SOLDIER_CHANGE = 18;
	/** 点击建筑收获 */
	public static final int BUILDING_REWARD = 19;
	/** 修理士兵 */
	public static final int REPAIR_SOLDIER = 20;
	/** 科研升级开始*/
	public static final int RESEARCH_LEVEL_UP = 21;
	/** 科研升级结束*/
	public static final int RESEARCH_LEVEL_UP_END = 22;
	/** 占领领土*/
	public static final int OCC_LAND = 23;
	/** 野外采集*/
	public static final int PICK = 24;
	/** 攻击玩家掠夺*/
	public static final int ATTACK_REWARD = 25;
	/** 野外战斗*/
	public static final int FIELD_FIGHT = 26;
	/** 消灭野外怪物*/
	public static final int KILL_MONSTER = 27;
	/** 攻击玩家基地胜利*/
	public static final int ATTACK_BASE_WIN = 28;
	/** 侦查玩家*/
	public static final int DETECT = 29;
	/** 攻击玩家*/
	public static final int ATTACK_PLAYER = 30;
	/** 损坏敌方部队*/
	public static final int DAMAGE_SIDE = 31;
	/** 摧毁敌方部队*/
	public static final int DESTROY_SIDE = 32;
	/** 攻占他服行政中心*/
	public static final int CAPTURE_OTHER_SERVER = 33;
	/** 通关副本*/
	public static final int COPY_PASS = 34;
	/** 个人事件中获得宝箱*/
	public static final int EVENT_GET_REWARDBOX = 35;
	/** 领取活跃宝箱*/
	public static final int GET_ACTIVE_REWARDBOX = 36;
	/** 使用道具*/
	public static final int USE_ITEM = 37;
	/** 统计玩家累计在线时间*/
	public static final int PLAYER_ONLIE_TIME = 38;
	
	/** 完成所有类型任务*/
	public static final int FINISH_TASK = 39;
	/** 战争模拟器胜利*/
	public static final int WAR_SIMULATOR_WIN = 40;
	/** 成为执行官（系统设置）*/
	public static final int EXECUTIVER = 41;
	/** 叛军攻打/击杀*/
	public static final int REBELS_ATTACK = 42;
	/** 植入体生产*/
	public static final int IMPLANTED_PRODUCE = 43;
	/** 植入体合成*/
	public static final int IMPLANTED_COMPOUND = 44;
	
	/** 获得植入体*/
	public static final int GET_IMPLANTED = 45;
	/** 生产材料*/
	public static final int MATERIALS_PRODUCTION = 46;
	/** 生产合成*/
	public static final int MATERIALS_COMPOUND = 47;
	/** 头目入侵*/
	public static final int LEADER_INVASION = 48;
	
	// -----------------------------------------------指挥官---------------------------------------------------------
	/** 指挥官升级*/
	public static final int EVENT_COMMAND_LEVELUP = 101; 
	/** 指挥官改名*/
	public static final int EVENT_COMMANDER_CHANGE_NAME =102; 
	/** 增加体力*/
	public static final int EVENT_COMMANDER_REFRESH_ENERGY = 103;
	/** 增加天赋点*/
	public static final int ADD_TALENT = 104;
	/** 穿戴装备*/
	public static final int USE_EQUIPMENT = 105;
	/** 卸下装备*/
	public static final int UNUSE_EQUIPMENT = 106;
	
	// ------------------------------------------------联盟-----------------------------------------------------------
	/** 加入联盟*/
	public static final int EVENT_ALLIANCE_JOIN = 201; 
	/** 离开联盟*/
	public static final int EVENT_ALLIANCE_LEFT = 202; 
	/** 创建联盟*/
	public static final int EVENT_CREATE_ALLIANCE = 203;
	/** 联盟升级*/
	public static final int EVENT_LEVELUP_ALLIANCE = 204;
	/** 推出联盟*/
	public static final int EVENT_EXIT_ALLIANCE = 205;
	
	/** 军团捐献*/
	public static final int ALLIANCE_DONATE = 206;
	/** 军团援建*/
	public static final int ALLIANCE_AID = 207;
	/** 发布军团招募*/
	public static final int ALLIANCE_PUBLISH_RECRUIT = 208;
	/** 发布集结*/
	public static final int ALLIANCE_BUILD_UP = 209;
	/** 刷新军团商店*/
	public static final int REFLRESH_LEAGUE_SHOP = 210;
	/** 军团商店兑换*/
	public static final int LEAGUE_SHOPPING = 211;
}
