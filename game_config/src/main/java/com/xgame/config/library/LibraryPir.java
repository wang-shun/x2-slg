package com.xgame.config.library;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class LibraryPir extends BaseFilePri{
	
	/**﻿属性id*/
	int id;
	/**属性名*/
	Object name;
	/**属性名id*/
	int name_id;
	/**属性类型（1：战斗属性，2：经济属性）*/
	int type;
	/**属性计算公式*/
	Object formula;
	/**属性类型(不再使用)*/
	int gs_type;
	/**战力参数（只用于计算装甲的战力）*/
	double gs_para;
	/**饼图参数（不再使用）*/
	double pie_para;
	/**客户端显示(0表示数值，1表示百分比)*/
	int client;
	/**客户端排序(从小到大排序)*/
	int index;
	/**属性指向目标（没有则为空）*/
	int  point_to;
	
	
	
	/**﻿属性id*/
	public int getId(){
		return id;
	}
	/**属性名*/
	@SuppressWarnings("unchecked")
	public <T> T getName(){
		return (T)name;
	}
	/**属性名id*/
	public int getName_id(){
		return name_id;
	}
	/**属性类型（1：战斗属性，2：经济属性）*/
	public int getType(){
		return type;
	}
	/**属性计算公式*/
	@SuppressWarnings("unchecked")
	public <T> T getFormula(){
		return (T)formula;
	}
	/**属性类型(不再使用)*/
	public int getGs_type(){
		return gs_type;
	}
	/**战力参数（只用于计算装甲的战力）*/
	public double getGs_para(){
		return gs_para;
	}
	/**饼图参数（不再使用）*/
	public double getPie_para(){
		return pie_para;
	}
	/**客户端显示(0表示数值，1表示百分比)*/
	public int getClient(){
		return client;
	}
	/**客户端排序(从小到大排序)*/
	public int getIndex(){
		return index;
	}
	/**属性指向目标（没有则为空）*/
	public int  getPoint_to(){
		return point_to;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}