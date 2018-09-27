package com.xgame.config.serverGroup;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ServerGroupPir extends BaseFilePri{
	
	/**﻿大区ID*/
	int groupId;
	/**战区名*/
	int groupName;
	/**起始坐标*/
	int pointX1;
	/***/
	int pointY1;
	/**战区名锚点*/
	int pointX2;
	/***/
	int pointY2;
	/**战区旋转*/
	int revolve;
	
	
	
	/**﻿大区ID*/
	public int getGroupId(){
		return groupId;
	}
	/**战区名*/
	public int getGroupName(){
		return groupName;
	}
	/**起始坐标*/
	public int getPointX1(){
		return pointX1;
	}
	/***/
	public int getPointY1(){
		return pointY1;
	}
	/**战区名锚点*/
	public int getPointX2(){
		return pointX2;
	}
	/***/
	public int getPointY2(){
		return pointY2;
	}
	/**战区旋转*/
	public int getRevolve(){
		return revolve;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}