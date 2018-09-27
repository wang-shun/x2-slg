package com.xgame.config.C100305;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
<<<<<<< .mine
 * @date 2017-04-11 15:09:57 
||||||| .r10899
 * @date 2017-03-31 16:47:47 
=======
 * @date 2017-05-19 15:18:02 
>>>>>>> .r11483
 */
public class C100305Pir extends BaseFilePri{
	
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
	/**驻军怪物组id（对应copyMonster表,怪物id）*/
	int monsterId;
	/**驻军数量*/
	int monsterNum;
	
	
	
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
	/**驻军怪物组id（对应copyMonster表,怪物id）*/
	public int getMonsterId(){
		return monsterId;
	}
	/**驻军数量*/
	public int getMonsterNum(){
		return monsterNum;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}