package com.xgame.config.armyActiveQuest;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ArmyActiveQuestPir extends BaseFilePri{
	
	/**﻿id*/
	int id;
	/**名称*/
	int name;
	/**任务类型（1：兵力总数，2：指定建筑到指定等级，3：指定科技到指定等级，4：消灭怪物，5：侦查玩家基地\营地\采集资源点，6:攻击玩家，7：完成所有类型任务，8：指挥官等级，9：世界采集次数，10：进攻玩家基地胜利，11：野外采集，12：攻击玩家掠夺，13：解锁科研，14：拥有植入体，15：获取活跃宝箱，16：占领领土，17：使用指定道具，18：击败敌方部队，19：击败他服敌方部队，20：修理伤兵，21：成为执政官，22：攻占他服行政中心）*/
	int type;
	/**v1*/
	Object v1;
	/**v2*/
	Object v2;
	/**单次完成增加活跃度*/
	int addActive;
	/**每日上限值*/
	int max;
	
	
	
	/**﻿id*/
	public int getId(){
		return id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**任务类型（1：兵力总数，2：指定建筑到指定等级，3：指定科技到指定等级，4：消灭怪物，5：侦查玩家基地\营地\采集资源点，6:攻击玩家，7：完成所有类型任务，8：指挥官等级，9：世界采集次数，10：进攻玩家基地胜利，11：野外采集，12：攻击玩家掠夺，13：解锁科研，14：拥有植入体，15：获取活跃宝箱，16：占领领土，17：使用指定道具，18：击败敌方部队，19：击败他服敌方部队，20：修理伤兵，21：成为执政官，22：攻占他服行政中心）*/
	public int getType(){
		return type;
	}
	/**v1*/
	@SuppressWarnings("unchecked")
	public <T> T getV1(){
		return (T)v1;
	}
	/**v2*/
	@SuppressWarnings("unchecked")
	public <T> T getV2(){
		return (T)v2;
	}
	/**单次完成增加活跃度*/
	public int getAddActive(){
		return addActive;
	}
	/**每日上限值*/
	public int getMax(){
		return max;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}