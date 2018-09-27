package com.xgame.config.armyShopTreasure;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ArmyShopTreasurePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**道具ID*/
	Object item_id;
	/**单次兑换道具数量*/
	int item_num;
	/**随机军团等级*/
	int needArmyLv;
	/**权重值*/
	int weight;
	/**兑换所需军团贡献*/
	int GX;
	/**限购次数*/
	int max;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**道具ID*/
	@SuppressWarnings("unchecked")
	public <T> T getItem_id(){
		return (T)item_id;
	}
	/**单次兑换道具数量*/
	public int getItem_num(){
		return item_num;
	}
	/**随机军团等级*/
	public int getNeedArmyLv(){
		return needArmyLv;
	}
	/**权重值*/
	public int getWeight(){
		return weight;
	}
	/**兑换所需军团贡献*/
	public int getGX(){
		return GX;
	}
	/**限购次数*/
	public int getMax(){
		return max;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}