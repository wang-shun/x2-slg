package com.xgame.config.serverEvent;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ServerEventPir extends BaseFilePri{
	
	/**﻿主键*/
	int id;
	/**服务器id组*/
	Object serverIds;
	/**事件组id*/
	Object eventTeamId;
	
	
	
	/**﻿主键*/
	public int getId(){
		return id;
	}
	/**服务器id组*/
	@SuppressWarnings("unchecked")
	public <T> T getServerIds(){
		return (T)serverIds;
	}
	/**事件组id*/
	@SuppressWarnings("unchecked")
	public <T> T getEventTeamId(){
		return (T)eventTeamId;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}