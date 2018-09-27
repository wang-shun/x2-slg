package com.xgame.config.init;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-06 16:18:55 
 */
public class InitPir extends BaseFilePri{
	
	/**﻿配置ID*/
	int id;
	/**初始黄金*/
	int value_1;
	/**初始稀土*/
	int value_2;
	/**初始钢材*/
	int value_3;
	/**初始石油*/
	int value_4;
	/**初始钻石*/
	int value_5;
	/**初始体力数*/
	int value_6;
	/**初始出征队列数*/
	int value_7;
	/**初始建筑队列数*/
	int value_8;
	/**初始科研队列*/
	int value_9;
	/**备用*/
	Object value_10;
	/**备用*/
	Object value_11;
	/**备用*/
	Object value_12;
	/**备用*/
	Object value_13;
	
	
	
	/**﻿配置ID*/
	public int getId(){
		return id;
	}
	/**初始黄金*/
	public int getValue_1(){
		return value_1;
	}
	/**初始稀土*/
	public int getValue_2(){
		return value_2;
	}
	/**初始钢材*/
	public int getValue_3(){
		return value_3;
	}
	/**初始石油*/
	public int getValue_4(){
		return value_4;
	}
	/**初始钻石*/
	public int getValue_5(){
		return value_5;
	}
	/**初始体力数*/
	public int getValue_6(){
		return value_6;
	}
	/**初始出征队列数*/
	public int getValue_7(){
		return value_7;
	}
	/**初始建筑队列数*/
	public int getValue_8(){
		return value_8;
	}
	/**初始科研队列*/
	public int getValue_9(){
		return value_9;
	}
	/**备用*/
	@SuppressWarnings("unchecked")
	public <T> T getValue_10(){
		return (T)value_10;
	}
	/**备用*/
	@SuppressWarnings("unchecked")
	public <T> T getValue_11(){
		return (T)value_11;
	}
	/**备用*/
	@SuppressWarnings("unchecked")
	public <T> T getValue_12(){
		return (T)value_12;
	}
	/**备用*/
	@SuppressWarnings("unchecked")
	public <T> T getValue_13(){
		return (T)value_13;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}