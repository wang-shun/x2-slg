package com.conf.app.util;

import java.text.SimpleDateFormat;

import com.conf.app.ConfigTool;
import com.conf.app.ParseFileBean;



/**
 *
 *2016-12-02  14:32:27
 *@author ye.yuan
 *
 */
public enum Kit {
	
	,;
	
	
	public static StringBuffer genUrl(ParseFileBean parseFileBean){
		return new StringBuffer().
		append(ConfigTool.packageParentName).
		append(parseFileBean.getPackageName())
		;
	}
	
	public static String dateStr(long t,String format)
	{
		return new SimpleDateFormat(format).format(t);
	}
}
