package com.xgame.config.eventRank;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:20 
 */
public class EventRankPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**事件ID*/
	int eventType;
	/**排名*/
	Object rank;
	/**领取奖励最低积分限制*/
	int limit;
	/**对应奖励*/
	Object rewards;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**事件ID*/
	public int getEventType(){
		return eventType;
	}
	/**排名*/
	@SuppressWarnings("unchecked")
	public <T> T getRank(){
		return (T)rank;
	}
	/**领取奖励最低积分限制*/
	public int getLimit(){
		return limit;
	}
	/**对应奖励*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards(){
		return (T)rewards;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}