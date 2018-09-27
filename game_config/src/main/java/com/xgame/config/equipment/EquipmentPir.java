package com.xgame.config.equipment;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:20 
 */
public class EquipmentPir extends BaseFilePri{
	
	/**﻿id*/
	int id;
	/**植入体名字*/
	int name;
	/**植入体描述*/
	int description;
	/**植入体等级*/
	int level;
	/**植入体品质（6个品质）*/
	int quality;
	/**植入体类型(战略:1，战争:2，生产:3，建造:4，特种:5，万能装备：7)*/
	int type;
	/**合成线（合成id相同的可以合成）*/
	int synthesis;
	/**植入体图标*/
	Object icon;
	/**植入体属性1（0建筑，1坦克，2战车，3飞机4玩家，填入属性id，例子，1,102,表示坦克负载）*/
	Object attr_1;
	/**锻造时间(单位秒)*/
	int time;
	/**分解获得物品(概率获得：20%的概率获得2个10020，80%的概率获得5个10021，22001,2,2000;22002,5,8000)*/
	int compose;
	/**锻造消耗黄金*/
	int cost_cash;
	/**锻造消耗稀土*/
	int cost_earth;
	/**锻造消耗钢材*/
	int cost_steel;
	/**锻造消耗石油*/
	int cost_oil;
	/**生产所需材料（材料1item表对应的id_材料1的数量；材料2item表对应的id_材料2的数量；）*/
	Object consumable_materials;
	/**升级所需装备&万能等级（填入需要的装等级;装备的数量,可以为空）*/
	Object consumable_equipment;
	/**奖励战力*/
	int gs_bonus;
	/**客户端筛选等级*/
	int select;
	
	
	
	/**﻿id*/
	public int getId(){
		return id;
	}
	/**植入体名字*/
	public int getName(){
		return name;
	}
	/**植入体描述*/
	public int getDescription(){
		return description;
	}
	/**植入体等级*/
	public int getLevel(){
		return level;
	}
	/**植入体品质（6个品质）*/
	public int getQuality(){
		return quality;
	}
	/**植入体类型(战略:1，战争:2，生产:3，建造:4，特种:5，万能装备：7)*/
	public int getType(){
		return type;
	}
	/**合成线（合成id相同的可以合成）*/
	public int getSynthesis(){
		return synthesis;
	}
	/**植入体图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**植入体属性1（0建筑，1坦克，2战车，3飞机4玩家，填入属性id，例子，1,102,表示坦克负载）*/
	@SuppressWarnings("unchecked")
	public <T> T getAttr_1(){
		return (T)attr_1;
	}
	/**锻造时间(单位秒)*/
	public int getTime(){
		return time;
	}
	/**分解获得物品(概率获得：20%的概率获得2个10020，80%的概率获得5个10021，22001,2,2000;22002,5,8000)*/
	public int getCompose(){
		return compose;
	}
	/**锻造消耗黄金*/
	public int getCost_cash(){
		return cost_cash;
	}
	/**锻造消耗稀土*/
	public int getCost_earth(){
		return cost_earth;
	}
	/**锻造消耗钢材*/
	public int getCost_steel(){
		return cost_steel;
	}
	/**锻造消耗石油*/
	public int getCost_oil(){
		return cost_oil;
	}
	/**生产所需材料（材料1item表对应的id_材料1的数量；材料2item表对应的id_材料2的数量；）*/
	@SuppressWarnings("unchecked")
	public <T> T getConsumable_materials(){
		return (T)consumable_materials;
	}
	/**升级所需装备&万能等级（填入需要的装等级;装备的数量,可以为空）*/
	@SuppressWarnings("unchecked")
	public <T> T getConsumable_equipment(){
		return (T)consumable_equipment;
	}
	/**奖励战力*/
	public int getGs_bonus(){
		return gs_bonus;
	}
	/**客户端筛选等级*/
	public int getSelect(){
		return select;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}