package com.xgame.config.activityRewards;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ActivityRewardsPir extends BaseFilePri{
	
	/**﻿id*/
	int id;
	/**名字id*/
	int nameId;
	/**需要个人积分*/
	int needPoint1;
	/**需要军团积分*/
	int needPoint2;
	/**奖励（道具id_道具数量,道具id_道具数量）*/
	Object rewards;
	
	
	
	/**﻿id*/
	public int getId(){
		return id;
	}
	/**名字id*/
	public int getNameId(){
		return nameId;
	}
	/**需要个人积分*/
	public int getNeedPoint1(){
		return needPoint1;
	}
	/**需要军团积分*/
	public int getNeedPoint2(){
		return needPoint2;
	}
	/**奖励（道具id_道具数量,道具id_道具数量）*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards(){
		return (T)rewards;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}