package com.xgame.config.armyExercise;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class ArmyExercisePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**奖励时间(部队达到后第多少秒给予奖励）*/
	int time;
	/**奖励（随机）*/
	Object reward;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**奖励时间(部队达到后第多少秒给予奖励）*/
	public int getTime(){
		return time;
	}
	/**奖励（随机）*/
	@SuppressWarnings("unchecked")
	public <T> T getReward(){
		return (T)reward;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}