package com.xgame.config.active;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ActivePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**名称*/
	int name;
	/**描述（好像没有用）*/
	int features;
	/**说明*/
	int description;
	/**任务类型（1:点击建筑收获，2:指定资源产量达到，3:生产某类士兵，4:生产某级别士兵，5:生产指定士兵，6:修理伤兵，7:装备生产，8:装备合成，9:拥有装备，10:材料生产，11:材料合成，12:解锁科研，13:科研升级，14:指定科技到指定等级，15:科研提升战力，16:建筑升级，17:指定建筑到指定等级，18:建造提升战力，19:使用某类型道具，20:使用指定道具，21:在线时间，22:占领领土，23:野外采集次数，24:野外采集，25:攻击玩家掠夺，26:野外战斗，27:消灭恐怖分子，28:进攻玩家基地胜利，29:侦查玩家，30:攻击玩家，31:损坏敌方部队，32:摧毁敌方部队，33:摧毁他服敌方部队，34:攻占他服行政中心，35:通关副本，36:个人事件，37:获取活跃宝箱，38:时间类任务，39:完成所有类型任务，40:指挥官等级，41:战争模拟器，42:捐献，43:军团援建，44:发布军团招募，45:集结进攻\防御，46:成为执政官，47:刷新军团商店，48:军团商店兑换，49:叛军攻打/击杀，50:头目入侵积分，51:军团捐献数值）*/
	int type;
	/**v1（任务类型参数）*/
	Object v1;
	/**v2（数量）*/
	Object v2;
	/**是否可前往*/
	int index;
	/**切换任务等级（行政大楼）*/
	Object limit;
	/**解锁条件*/
	Object unlock;
	/**单次奖励活跃度*/
	int singleRewards;
	/**任务图标*/
	Object icon;
	/**跳转对象*/
	Object goto1;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**描述（好像没有用）*/
	public int getFeatures(){
		return features;
	}
	/**说明*/
	public int getDescription(){
		return description;
	}
	/**任务类型（1:点击建筑收获，2:指定资源产量达到，3:生产某类士兵，4:生产某级别士兵，5:生产指定士兵，6:修理伤兵，7:装备生产，8:装备合成，9:拥有装备，10:材料生产，11:材料合成，12:解锁科研，13:科研升级，14:指定科技到指定等级，15:科研提升战力，16:建筑升级，17:指定建筑到指定等级，18:建造提升战力，19:使用某类型道具，20:使用指定道具，21:在线时间，22:占领领土，23:野外采集次数，24:野外采集，25:攻击玩家掠夺，26:野外战斗，27:消灭恐怖分子，28:进攻玩家基地胜利，29:侦查玩家，30:攻击玩家，31:损坏敌方部队，32:摧毁敌方部队，33:摧毁他服敌方部队，34:攻占他服行政中心，35:通关副本，36:个人事件，37:获取活跃宝箱，38:时间类任务，39:完成所有类型任务，40:指挥官等级，41:战争模拟器，42:捐献，43:军团援建，44:发布军团招募，45:集结进攻\防御，46:成为执政官，47:刷新军团商店，48:军团商店兑换，49:叛军攻打/击杀，50:头目入侵积分，51:军团捐献数值）*/
	public int getType(){
		return type;
	}
	/**v1（任务类型参数）*/
	@SuppressWarnings("unchecked")
	public <T> T getV1(){
		return (T)v1;
	}
	/**v2（数量）*/
	@SuppressWarnings("unchecked")
	public <T> T getV2(){
		return (T)v2;
	}
	/**是否可前往*/
	public int getIndex(){
		return index;
	}
	/**切换任务等级（行政大楼）*/
	@SuppressWarnings("unchecked")
	public <T> T getLimit(){
		return (T)limit;
	}
	/**解锁条件*/
	@SuppressWarnings("unchecked")
	public <T> T getUnlock(){
		return (T)unlock;
	}
	/**单次奖励活跃度*/
	public int getSingleRewards(){
		return singleRewards;
	}
	/**任务图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**跳转对象*/
	@SuppressWarnings("unchecked")
	public <T> T getGoto1(){
		return (T)goto1;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}