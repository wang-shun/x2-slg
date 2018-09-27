package com.xgame.config.CXlanguage_ZhCN;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-31 16:47:47 
 */
public class CXlanguage_ZhCNPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**key*/
	Object name;
	/**内容*/
	Object desc;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**key*/
	@SuppressWarnings("unchecked")
	public <T> T getName(){
		return (T)name;
	}
	/**内容*/
	@SuppressWarnings("unchecked")
	public <T> T getDesc(){
		return (T)desc;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}