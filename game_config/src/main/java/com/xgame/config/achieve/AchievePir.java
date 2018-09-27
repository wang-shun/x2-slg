package com.xgame.config.achieve;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:17 
 */
public class AchievePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**解锁1星（task表id）*/
	int unlock1;
	/**解锁2星（task表id）*/
	int unlock2;
	/**解锁3星并激活徽章（task表id）*/
	int unlock3;
	/**徽章图标*/
	Object icon;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**解锁1星（task表id）*/
	public int getUnlock1(){
		return unlock1;
	}
	/**解锁2星（task表id）*/
	public int getUnlock2(){
		return unlock2;
	}
	/**解锁3星并激活徽章（task表id）*/
	public int getUnlock3(){
		return unlock3;
	}
	/**徽章图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}