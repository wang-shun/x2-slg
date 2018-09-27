package com.xgame.config.limitRank;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class LimitRankPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**排行活动类型*/
	int activityType;
	/**排行归属(1个人2军团)*/
	int belong;
	/**类型（与task表相同，调取通用任务类型）*/
	int type;
	/**数值1*/
	Object v1;
	/**数值2*/
	Object v2;
	/**奖励积分*/
	int score;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**排行活动类型*/
	public int getActivityType(){
		return activityType;
	}
	/**排行归属(1个人2军团)*/
	public int getBelong(){
		return belong;
	}
	/**类型（与task表相同，调取通用任务类型）*/
	public int getType(){
		return type;
	}
	/**数值1*/
	@SuppressWarnings("unchecked")
	public <T> T getV1(){
		return (T)v1;
	}
	/**数值2*/
	@SuppressWarnings("unchecked")
	public <T> T getV2(){
		return (T)v2;
	}
	/**奖励积分*/
	public int getScore(){
		return score;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}