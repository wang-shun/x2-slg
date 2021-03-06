package com.xgame.config.activeRewards;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ActiveRewardsPir extends BaseFilePri{
	
	/**﻿宝箱ID*/
	int id;
	/**名称*/
	int name;
	/**活跃度*/
	int active;
	/**奖励(1-5级基地)*/
	Object rewards1;
	/**奖励(6-10级)*/
	Object rewards2;
	/**奖励(11-15级)*/
	Object rewards3;
	/**奖励(15-20级)*/
	Object rewards4;
	/**奖励(21-25级)*/
	Object rewards5;
	/**奖励(25-30级)*/
	Object rewards6;
	
	
	
	/**﻿宝箱ID*/
	public int getId(){
		return id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**活跃度*/
	public int getActive(){
		return active;
	}
	/**奖励(1-5级基地)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards1(){
		return (T)rewards1;
	}
	/**奖励(6-10级)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards2(){
		return (T)rewards2;
	}
	/**奖励(11-15级)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards3(){
		return (T)rewards3;
	}
	/**奖励(15-20级)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards4(){
		return (T)rewards4;
	}
	/**奖励(21-25级)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards5(){
		return (T)rewards5;
	}
	/**奖励(25-30级)*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards6(){
		return (T)rewards6;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}