package com.xgame.playersearch.util;

/**
 * 游戏当中产生日志的来源
 * @author jacky.jiang
 *
 */
public enum GameLogSource {

	DEFAULT(""),
	
	/**
	 * 使用弧顶宝箱
	 */
	USE_FIX_BOX("使用弧顶宝箱"),
	
	/**
	 * 使用随机宝箱
	 */
	USE_RANDOM_BOX("使用随机宝箱"),
	
	/**
	 * 使用道具
	 */
	USE_ITEM("使用道具"),
	
	/**
	 *  工业工厂产出
	 */
	INDUSTRY_OUTPUT("工业工厂产出"),
	
	/**
	 * 科技升级奖励
	 */
	TECH_LEVEL_UP("科技升级奖励"),
	
	/**
	 * 取消科技升级
	 */
	CANCEL_TECH_LEVEL_UP("取消科技升级"),
	
	/**
	 * 建筑升级奖励
	 */
	BUILD_LEVEL_UP("建筑升级奖励"),
	
	/**
	 * 取消建筑升级
	 */
	CANCEL_BUILD_LEVEL_UP("取消建筑升级"),
	
	/**
	 * 移除建筑奖励
	 */
	REMOVE_BUILD("移除建筑奖励"),
	
	/**
	 * 购买材料
	 */
	BUY_FRAGMENT("购买材料"),
	
	/**
	 * 合成材料
	 */
	COMPOSE_FRAGMENT("合成材料"),
	
	/**
	 * 装备锻造
	 */
	FORGING_EQUIPMENT("装备锻造"),
	
	/**
	 * 合成装备
	 */
	COMPOSE_EQUIPMENT("合成装备"),
	
	/**
	 * 分解装备
	 */
	DECOMPOSE_EQUIPMENT("分解装备"),
	
	/**
	 * 购买万能植入体材料
	 */
	
	BUY_SPECIAL_EQUIPMENT("购买万能植入体材料"),
	
	/**
	 * 商城购买
	 */
	SHOP_BUY("商城购买"),
	
	/**
	 * GM
	 */
	GM("GM"),
	
	/**
	 * 改名
	 */
	CHANAGE_NAME("改名"),
	
	/**
	 * 改变形象
	 */
	CHANGE_STYLE("改变形象"),
	
	/**
	 * 使用统帅道具
	 */
	USE_CAPTAIN("使用统帅道具"),
	
	/**
	 * 增加统帅点
	 */
	ADD_CAPTAIN_POINT("增加统帅点"),
	
	/**
	 * 切换天赋页
	 */
	SWITCH_TALENT_PAGE("切换天赋页"),
	
	/**
	 * 材料产出
	 */
	FRAGMENT_OUTPUT("材料产出"),
	
	/**
	 * 创建联盟
	 */
	CREATE_ALLILANCE("创建联盟"),
	
	/**
	 * 联盟
	 */
	ALLIANCE_JOIN("联盟"),
	
	/**
	 * 联盟捐献
	 */
	ALLIANCE_DONATE("联盟捐献"),
	
	/**
	 * 编辑联盟信息
	 */
	EDIT_ALLIANCE_INFO("编辑联盟信息"),
	
	/**
	 * 生产坦克
	 */
	CAMP_OUTPUT_TANK("生产坦克"),
	
	/**
	 * 生产suv
	 */
	CAMP_OUTPUT_SUV("生产suv"),
	
	/**
	 * 生产飞机
	 */
	CAMP_OUTPUT_PLANE("生产飞机"),
	
	/**
	 * 解锁工业工厂生产槽位
	 */
	UNLOCK_INDUSTRY_POSITION("解锁工业工厂生产槽位"),
	
	/**
	 * 采矿场产出
	 */
	MINE_OUTPUT("采矿场产出"),
	
	/**
	 * 修咖士兵
	 */
	MODIFY_SOLDIER("修咖士兵"),
	
	/**
	 * 销毁士兵
	 */
	DESTORY_SOLDIER("销毁士兵"),
	
	/**
	 * 改造士兵
	 */
	REFIT_SOLDIER("改造士兵"),
	
	/**
	 * 联盟交易
	 */
	ALLIANCE_TRADE("联盟交易"),
	
	/**
	 * 加速时间队列
	 */
	SPEED_UP_TIMER_TASK("加速时间队列"),
	
	/**
	 * 家园战斗
	 */
	COUNTRY_WAR("家园战斗"),
	
	/**
	 * 出征
	 */
	MARCH("出征"),
	
	/**
	 * 采集
	 */
	EXPLORER("采集");
	
	private String describe;
	
	private GameLogSource(String describe){
		this.describe = describe;
	}
	
	public String getDescribe(){
		return this.describe;
	}
}
