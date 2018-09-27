package com.xgame.config.mailLanguage_EN;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-19 15:18:03 
 */
public class MailLanguage_ENPir extends BaseFilePri{
	
	/**﻿标题id*/
	int sampleId;
	/**标题*/
	Object title;
	/**内容*/
	Object content;
	/**跳转链接(1,坐标跳转)*/
	int goto1;
	/**使用模板*/
	int useSimple;
	/**图标1*/
	Object icon1;
	
	
	
	/**﻿标题id*/
	public int getSampleId(){
		return sampleId;
	}
	/**标题*/
	@SuppressWarnings("unchecked")
	public <T> T getTitle(){
		return (T)title;
	}
	/**内容*/
	@SuppressWarnings("unchecked")
	public <T> T getContent(){
		return (T)content;
	}
	/**跳转链接(1,坐标跳转)*/
	public int getGoto1(){
		return goto1;
	}
	/**使用模板*/
	public int getUseSimple(){
		return useSimple;
	}
	/**图标1*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon1(){
		return (T)icon1;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}