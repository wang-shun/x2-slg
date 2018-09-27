package com.xgame.config.language;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class LanguagePir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**地区域名*/
	Object DZYM;
	/**地区标识*/
	Object DQQZ;
	/**语种显示*/
	int DQYY;
	/**客户端语言设置标识*/
	Object YYBZ;
	/**策划多语言文件名*/
	Object txt1;
	/**程序UI多语言文件名*/
	Object txt2;
	/**程序邮件多语言文件名*/
	Object txt3;
	/**程序提示公告多语言文件名*/
	Object txt4;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**地区域名*/
	@SuppressWarnings("unchecked")
	public <T> T getDZYM(){
		return (T)DZYM;
	}
	/**地区标识*/
	@SuppressWarnings("unchecked")
	public <T> T getDQQZ(){
		return (T)DQQZ;
	}
	/**语种显示*/
	public int getDQYY(){
		return DQYY;
	}
	/**客户端语言设置标识*/
	@SuppressWarnings("unchecked")
	public <T> T getYYBZ(){
		return (T)YYBZ;
	}
	/**策划多语言文件名*/
	@SuppressWarnings("unchecked")
	public <T> T getTxt1(){
		return (T)txt1;
	}
	/**程序UI多语言文件名*/
	@SuppressWarnings("unchecked")
	public <T> T getTxt2(){
		return (T)txt2;
	}
	/**程序邮件多语言文件名*/
	@SuppressWarnings("unchecked")
	public <T> T getTxt3(){
		return (T)txt3;
	}
	/**程序提示公告多语言文件名*/
	@SuppressWarnings("unchecked")
	public <T> T getTxt4(){
		return (T)txt4;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}