package com.xgame.config.limitWelfare;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class LimitWelfarePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**活动调用ID*/
	int activityType;
	/**调用类型1参数2纯文本*/
	int readType;
	/**参数类型*/
	int type;
	/**参数配置（参数调整的是基础配置的值or最终结果）*/
	Object value;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**活动调用ID*/
	public int getActivityType(){
		return activityType;
	}
	/**调用类型1参数2纯文本*/
	public int getReadType(){
		return readType;
	}
	/**参数类型*/
	public int getType(){
		return type;
	}
	/**参数配置（参数调整的是基础配置的值or最终结果）*/
	@SuppressWarnings("unchecked")
	public <T> T getValue(){
		return (T)value;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}