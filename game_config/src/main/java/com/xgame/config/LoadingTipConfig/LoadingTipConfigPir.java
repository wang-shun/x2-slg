package com.xgame.config.LoadingTipConfig;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class LoadingTipConfigPir extends BaseFilePri{
	
	/**﻿提示ID*/
	int id;
	/**提示|msg(string)*/
	int msg;
	/**vip*/
	int vip;
	
	
	
	/**﻿提示ID*/
	public int getId(){
		return id;
	}
	/**提示|msg(string)*/
	public int getMsg(){
		return msg;
	}
	/**vip*/
	public int getVip(){
		return vip;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}