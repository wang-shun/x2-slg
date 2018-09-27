package com.xgame.config.touXiang;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**图标ID*/
	Object icon;
	/**图片ID*/
	Object TID;
	/**大类*/
	int type;
	/**部位（1脸型 2 胡子 3 帽子 4 眼睛 5衣服 6嘴 7 鼻子 8 头发 9 眼镜 10 前发+后发 11 背景）*/
	int type1;
	/**层级优先（数字越大越上层）*/
	int cengJi;
	/**解锁所需VIP等级*/
	int needVIP;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**图标ID*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**图片ID*/
	@SuppressWarnings("unchecked")
	public <T> T getTID(){
		return (T)TID;
	}
	/**大类*/
	public int getType(){
		return type;
	}
	/**部位（1脸型 2 胡子 3 帽子 4 眼睛 5衣服 6嘴 7 鼻子 8 头发 9 眼镜 10 前发+后发 11 背景）*/
	public int getType1(){
		return type1;
	}
	/**层级优先（数字越大越上层）*/
	public int getCengJi(){
		return cengJi;
	}
	/**解锁所需VIP等级*/
	public int getNeedVIP(){
		return needVIP;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}