package com.xgame.config.dailyTask;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:20 
 */
public class DailyTaskPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**名称*/
	int name;
	/**品质（1：基础任务,2：普通任务,3：特殊任务,4：传说任务,5：史诗任务）*/
	int quality;
	/**权重*/
	int chance;
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
	/**完成时间*/
	int time;
	/**图标（图集Altas_Task）*/
	Object icon;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**品质（1：基础任务,2：普通任务,3：特殊任务,4：传说任务,5：史诗任务）*/
	public int getQuality(){
		return quality;
	}
	/**权重*/
	public int getChance(){
		return chance;
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
	/**完成时间*/
	public int getTime(){
		return time;
	}
	/**图标（图集Altas_Task）*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}