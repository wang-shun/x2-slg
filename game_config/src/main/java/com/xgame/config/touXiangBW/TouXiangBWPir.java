package com.xgame.config.touXiangBW;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:05 
 */
public class TouXiangBWPir extends BaseFilePri{
	
	/**﻿分类 */
	int type1;
	/**tab排序*/
	int paiXu;
	/**名字多语言*/
	Object name;
	/**双击是否可取消*/
	int quXiao;
	
	
	
	/**﻿分类 */
	public int getType1(){
		return type1;
	}
	/**tab排序*/
	public int getPaiXu(){
		return paiXu;
	}
	/**名字多语言*/
	@SuppressWarnings("unchecked")
	public <T> T getName(){
		return (T)name;
	}
	/**双击是否可取消*/
	public int getQuXiao(){
		return quXiao;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}