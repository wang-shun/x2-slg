package com.xgame.config.armyBuilding;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ArmyBuildingPir extends BaseFilePri{
	
	/**﻿建筑ID*/
	int ID;
	/**建筑名*/
	int name;
	/**建筑描述*/
	int desc;
	/**建筑类型（1科技大楼 2 演习基地 3 军团仓库 4 超级矿 5 要塞 6火炮 7 速射 8 防空）*/
	int buildingType;
	/**建筑属性V1（建筑类型=4时 1 石油 2 稀土 3 钢材 4黄金）*/
	Object v1;
	/**建筑属性V2*/
	Object v2;
	/**客户端显示tab(1功能建筑 2 资源建筑 3 防御工事)*/
	int UItab;
	/**客户端显示顺序*/
	int tab;
	/**解锁所需军团等级*/
	int needLv;
	/**野外展示*/
	Object showID1;
	/**UI展示*/
	Object showID2;
	/**换位所需消耗军团资金*/
	int needArmyMoney2;
	/**军团建筑最大等级*/
	int maxLv;
	/**升级所需军团资金*/
	Object LvUpArmyMoney;
	
	
	
	/**﻿建筑ID*/
	public int getID(){
		return ID;
	}
	/**建筑名*/
	public int getName(){
		return name;
	}
	/**建筑描述*/
	public int getDesc(){
		return desc;
	}
	/**建筑类型（1科技大楼 2 演习基地 3 军团仓库 4 超级矿 5 要塞 6火炮 7 速射 8 防空）*/
	public int getBuildingType(){
		return buildingType;
	}
	/**建筑属性V1（建筑类型=4时 1 石油 2 稀土 3 钢材 4黄金）*/
	@SuppressWarnings("unchecked")
	public <T> T getV1(){
		return (T)v1;
	}
	/**建筑属性V2*/
	@SuppressWarnings("unchecked")
	public <T> T getV2(){
		return (T)v2;
	}
	/**客户端显示tab(1功能建筑 2 资源建筑 3 防御工事)*/
	public int getUItab(){
		return UItab;
	}
	/**客户端显示顺序*/
	public int getTab(){
		return tab;
	}
	/**解锁所需军团等级*/
	public int getNeedLv(){
		return needLv;
	}
	/**野外展示*/
	@SuppressWarnings("unchecked")
	public <T> T getShowID1(){
		return (T)showID1;
	}
	/**UI展示*/
	@SuppressWarnings("unchecked")
	public <T> T getShowID2(){
		return (T)showID2;
	}
	/**换位所需消耗军团资金*/
	public int getNeedArmyMoney2(){
		return needArmyMoney2;
	}
	/**军团建筑最大等级*/
	public int getMaxLv(){
		return maxLv;
	}
	/**升级所需军团资金*/
	@SuppressWarnings("unchecked")
	public <T> T getLvUpArmyMoney(){
		return (T)LvUpArmyMoney;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}