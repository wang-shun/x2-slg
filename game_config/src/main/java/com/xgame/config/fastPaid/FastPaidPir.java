package com.xgame.config.fastPaid;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class FastPaidPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**价格（单位，钻石）*/
	int price;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**价格（单位，钻石）*/
	public int getPrice(){
		return price;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}