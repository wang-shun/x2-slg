package com.xgame.config.C101101;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-28 19:58:44 
 */
public class C101101Pir extends BaseFilePri{
	
	/**﻿建筑id（building表）*/
	int buildingId;
	/**坐标X*/
	int x;
	/**坐标Y*/
	int y;
	/**建筑物等级*/
	int buildingLv;
	/**资源仓库中含有资源的数量*/
	int resource;
	
	
	
	/**﻿建筑id（building表）*/
	public int getBuildingId(){
		return buildingId;
	}
	/**坐标X*/
	public int getX(){
		return x;
	}
	/**坐标Y*/
	public int getY(){
		return y;
	}
	/**建筑物等级*/
	public int getBuildingLv(){
		return buildingLv;
	}
	/**资源仓库中含有资源的数量*/
	public int getResource(){
		return resource;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}