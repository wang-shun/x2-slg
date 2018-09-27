package com.xgame.logic.server.game.playerattribute.constant;


/**
 * 属性枚举
 * @author zehong.he
 *
 */
public enum AttributesEnum {
	
	/**
	 *提供功率
	 */
	POWER(101),
	
	/**
	 *提供负载
	 */
	LOAD(102),
	
	/**
	 *消耗功率
	 */
	POWER_CONSUME(133),
	
	/**
	 *消耗负载
	 */
	LOAD_CONSUME(134),
	
	/**
	 *攻击
	 */
	ATTACK(104),
	
	/**
	 *防御
	 */
	DEFENSE(105),
	
	/**
	 *血量
	 */
	HP(106),
	
	/**
	 *电磁攻击
	 */
	ELECTRICITY_DAMAGE(107),
	
	/**
	 *电磁抗性
	 */
	ELECTRICITY_DEFENSE(108),
	
	/**
	 *动能攻击
	 */
	ENERGY_DAMAGE(109),
	
	/**
	 *动能抗性
	 */
	ENERGY_DEFENSE(110),
	
	/**
	 *热能伤害
	 */
	HEAT_DAMAGE(111),
	
	/**
	 *热能抗性
	 */
	HEAT_DEFENSE(112),
	
	/**
	 *激光伤害
	 */
	LASER_DAMAGE(113),
	
	/**
	 *激光抗性
	 */
	LASER_DEFENSE(114),
	
	/**
	 *命中等级
	 */
	HIT(115),
	
	/**
	 *闪避等级
	 */
	DODGE(116),
	
	/**
	 *暴击伤害倍数
	 */
	CRIT(117),
	
	/**
	 *暴击等级
	 */
	CRITICAL(118),
	
	/**
	 *韧性等级
	 */
	TOUGHNESS(119),
	
	/**
	 *移动速度
	 */
	SPEED_BASE(122),
	
	/**
	 *战斗移动速度
	 */
	SPEED_FIGHT(123),
	
	/**
	 *打怪移动速度
	 */
	SPEED_MONSTER(124),
	
	/**
	 *采集移动速度
	 */
	SPEED_GATHER(125),
	
	/**
	 * 集结行军速度
	 */
	SPEED_TEAM_ATTACK(135),
	
	/**
	 *采集负重
	 */
	WEIGHT(127),
	
	/**
	 *索敌数量
	 */
	SEEKING_NUM(129),
	
	/**
	 *雷达强度
	 */
	RADAR_INTENSITY(131),
	
	/**
	 *车体半径
	 */
	RADIUS(132),

	//---------------------------------------//
	/**
	 *黄金采集速度提高
	 */
	CASH_GATHER_RATE(305),
	/**
	 *石油采集速度提高
	 */
	OIL_GATHER_RATE(306),
	/**
	 *稀土采集速度提高
	 */
	EARTH_GATHER_RATE(307),
	/**
	 *钢材采集速度提高
	 */
	STEEL_GATHER_RATE(308),
	/**
	 *黄金仓库保护比例提高
	 */
	CASH_SAFEGUARD_RATE(310),
	/**
	 *稀土仓库保护比例提高
	 */
	EARTH_SAFEGUARD_RATE(311),
	/**
	 *石油仓库保护比例提高
	 */
	OIL_SAFEGUARD_RATE(312),
	/**
	 *钢材仓库保护比例提高
	 */
	STEEL_SAFEGUARD_RATE(313),
	/**
	 *部队生产速度加速
	 */
	ARMY_PRODUCE_RATE(314),
	/**
	 *侦察行军速度提高
	 */
	ARMY_SPY_MARCH_SPEED(319),
	/**
	 *打怪行军时间减少
	 */
	ARMY_MONSTER_MARCH_TIME(320),
	/**
	 *采集行军时间减少
	 */
	ARMY_GATHER_MARCH_TIME(321),
	/**
	 *战斗行军时间减少
	 */
	ARMY_FIGHT_MARCH_TIME(322),
	/**
	 *侦察行军时间减少
	 */
	ARMY_SPY_MARCH_TIME(323),
	/**
	 * 集结行军时间减少
	 */
	ARMY_SPY_MARCH_TIME_ATTACK(350),
	/**
	 *建筑建造速度加速
	 */
	BUILDING_TIME(324),
	/**
	 *收治伤兵数量上限提高
	 */
	HOSPITAL_MAX_NUM(326),
	/**
	 *伤兵恢复速度提高
	 */
	HOSPITAL_RECOVER_RATE(327),
	/**
	 *出征部队人数
	 */
	MATCH_ARMY_MAX_NUM(328),
	/**
	 *科技研究时间减少
	 */
	SCIENCE_TIME(329),
	/**
	 *兵工厂单次生产士兵上限
	 */
	ARMORY_CAPACITY(330),
	/**
	 *屯兵消耗资源降低
	 */
	ARMY_CONSUME(331),
	/**
	 *兵种产消耗减低%
	 */
	ARMY_PRODUCE_DECREASE(335),
	/**
	 *行军队列增加
	 */
	MATCH_QUEUE(336),
	/**
	 *勘探开发院采集容量
	 */
	CAPACITY(337),
//	/**
//	 *减少建筑升级消耗黄金
//	 */
//	BUILDING_CASH_DECREASE(338),
//	/**
//	 *减少建筑升级消耗稀土
//	 */
//	BUILDING_EARTH_DECREASE(339),
//	/**
//	 *减少建筑升级消耗石油
//	 */
//	BUILDING_OIL_DECREASE(340),
//	/**
//	 *减少建筑升级消耗钢材
//	 */
//	BUILDING_STEEL_DECREASE(341),
	/**
	 *集结军队数量
	 */
	CONCENTRATE_ARMY(342),
	
	/**
	 * 建筑杜列
	 */
	BUILDING_QUEUE(343),
	
	/**
	 * 科技队列
	 */
	TECH_QUEUE(344),
	
	/**
	 * 矿车采集速度
	 */
	CAR_GATHER_RATE(351),
	
	/**
	 * 防守驻军数量
	 */
	GUARD_MAX_NUM(352),
	
	/**
	 * 增加军团援建减少的时间
	 */
	HELP_REDUCE_BUILDING_TIME(353),
	
	/**
	 * 装甲修理减耗
	 */
	HOSPITAL_RESOURCE_DECREASE(354),
	
	//-------------------------------------------/
	/**
	 *提供功率
	 */
	POWER_PER(201,true),
	/**
	 *提供负载
	 */
	LOAD_PER(202,true),
	/**
	 *消耗功率
	 */
	POWER_CONSUME_PER(233,true),
	/**
	 *消耗负载
	 */
	LOAD_CONSUME_PER(234,true),
	/**
	 *伤害
	 */
	DAMAGE_PER(204,true),
	/**
	 *防御
	 */
	DEFENSE_PER(205,true),
	/**
	 *血量
	 */
	HP_PER(206,true),
	/**
	 *电磁伤害
	 */
	ELECTRICITY_DAMAGE_PER(207,true),
	/**
	 *电磁抗性
	 */
	ELECTRICITY_DEFENSE_PER(208,true),
	/**
	 *动能伤害
	 */
	ENERGY_DAMAGE_PER(209,true),
	/**
	 *动能抗性
	 */
	ENERGY_DEFENSE_PER(210,true),
	/**
	 *热能伤害
	 */
	HEAT_DAMAGE_PER(211,true),
	/**
	 *热能抗性
	 */
	HEAT_DEFENSE_PER(212,true),
	/**
	 *激光伤害
	 */
	LASER_DAMAGE_PER(213,true),
	/**
	 *激光抗性
	 */
	LASER_DEFENSE_PER(214,true),
	/**
	 *命中等级
	 */
	HIT_PER(215,true),
	/**
	 *闪避等级
	 */
	DODGE_PER(216,true),
	/**
	 *暴击攻击倍数
	 */
	CRIT_PER(217,true),
	/**
	 *暴击等级
	 */
	CRITICAL_PER(218,true),
	/**
	 *韧性等级
	 */
	TOUGHNESS_PER(219,true),
	/**
	 *移动速度
	 */
	SPEED_BASE_PER(222,true),
	/**
	 *战斗移动速度
	 */
	SPEED_FIGHT_PER(223,true),
	/**
	 *打怪移动速度
	 */
	SPEED_MONSTER_PER(224,true),
	/**
	 *采集移动速度
	 */
	SPEED_GATHER_PER(225,true),
	/**
	 * 集结行军速度
	 */
	SPEED_MASS_PER(235,true),
	/**
	 *采集负重
	 */
	WEIGHT_PER(227,true),
	/**
	 *索敌数量
	 */
	SEEKING_NUM_PER(229,true),
	/**
	 *雷达强度
	 */
	RADAR_INTENSITY_PER(231,true),
	/**
	 *车体半径
	 */
	RADIUS_PER(232,true),
	
	;
	
	/**
	 * 属性id
	 */
	private int id;
	
	/**
	 * 是否是加成属性
	 */
	private boolean isAddAttr;
	
	private AttributesEnum(int id){
		this.id = id;
	}
	
	private AttributesEnum(int id,boolean isAddAttr){
		this.id = id;
		this.isAddAttr = isAddAttr;
	}
	
	public int getId(){
		return this.id;
	}
	
	public boolean isAddAttr() {
		return isAddAttr;
	}
	
	public static AttributesEnum getCode(int code){
		for(AttributesEnum o : AttributesEnum.values()){
			if(o.getId() == code){
				return o;
			}
		}
		return null;
	}

	public static void main(String[] a){
		for(AttributesEnum dd : AttributesEnum.values()){
			if(dd.isAddAttr == false){
				continue;
			}
			if(dd.id == 305){
				System.out.println();
			}
			System.out.println(dd.id);
		}
	}
}
