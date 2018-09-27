package com.xgame.utils;

import java.io.PrintWriter;
import java.io.StringWriter;


public class StringUtil {
	
	/**
	 * 异常转日志
	 * @param e
	 * @return
	 */
	public static String getExceptionTrace(Throwable e){
		if(e==null)return "no Exception";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 字符串比较
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean compareString(String str1, String str2) {
		if (str1 == null || str2 == null) {
			if (str1 == str2) {
				return true;
			}
		} else if (str1.equals(str2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否null或者是否为空字符
	 * @param string
	 * @return
	 */
	public static boolean isEmptyOrNull(String string){
		if(string == null || "".equals(string)){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 获得一个JSON的数据 
	 * <br>注意：这里的格式是没有最外层的括号的如下:
	 * <br>“x:123, y:123, name:"张三" ”
	 * @param txt
	 * @return 
	 * 
	 */		
	public static String formatToJson(String txt)
	{
		txt = txt.substring(1, txt.length() - 1);
		txt = "{" + txt + "}"; 
		//txt = txt.replaceAll(Symbol.DOUHAO_REG, Symbol.DOUHAO);
		String parse = txt.replaceAll("([{,，])\\s*([0-9a-zA-Z一-龟]+)\\s*:", "$1\"$2\":" );
		return parse;
	}
}
