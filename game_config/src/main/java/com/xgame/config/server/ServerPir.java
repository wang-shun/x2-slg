package com.xgame.config.server;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ServerPir extends BaseFilePri{
	
	/**﻿服务器ID*/
	int id;
	/**服务器名*/
	int serverName;
	/**所属大区*/
	int group;
	/**服务器锚点*/
	int pointX;
	/***/
	int pointY;
	/**UI锚点*/
	int UIPoint;
	/**图标*/
	Object icon;
	
	
	
	/**﻿服务器ID*/
	public int getId(){
		return id;
	}
	/**服务器名*/
	public int getServerName(){
		return serverName;
	}
	/**所属大区*/
	public int getGroup(){
		return group;
	}
	/**服务器锚点*/
	public int getPointX(){
		return pointX;
	}
	/***/
	public int getPointY(){
		return pointY;
	}
	/**UI锚点*/
	public int getUIPoint(){
		return UIPoint;
	}
	/**图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}