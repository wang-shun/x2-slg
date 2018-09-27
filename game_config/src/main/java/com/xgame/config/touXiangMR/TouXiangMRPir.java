package com.xgame.config.touXiangMR;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangMRPir extends BaseFilePri{
	
	/**﻿默认头像ID*/
	int id;
	/**大类ID*/
	int type;
	/**组合*/
	Object zuHe;
	
	
	
	/**﻿默认头像ID*/
	public int getId(){
		return id;
	}
	/**大类ID*/
	public int getType(){
		return type;
	}
	/**组合*/
	@SuppressWarnings("unchecked")
	public <T> T getZuHe(){
		return (T)zuHe;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}