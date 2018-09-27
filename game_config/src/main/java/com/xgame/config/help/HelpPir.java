package com.xgame.config.help;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class HelpPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**类型（1纯文本帮助 2 特例）*/
	int type;
	/**标题多语言ID*/
	int title;
	/**显示关闭按钮1:true;0:false*/
	int showclose;
	/**点周围关闭1:true;0:false*/
	int hidecommon;
	/**帮助文本内容多语言ID*/
	int des;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**类型（1纯文本帮助 2 特例）*/
	public int getType(){
		return type;
	}
	/**标题多语言ID*/
	public int getTitle(){
		return title;
	}
	/**显示关闭按钮1:true;0:false*/
	public int getShowclose(){
		return showclose;
	}
	/**点周围关闭1:true;0:false*/
	public int getHidecommon(){
		return hidecommon;
	}
	/**帮助文本内容多语言ID*/
	public int getDes(){
		return des;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}