package com.xgame.config.util;

import java.util.Map;

/**
 *
 *2016-7-25  10:03:54
 *@author ye.yuan
 *
 */
public final class ConfigUtil {
	
	/**
	 * 从0等级开始转换配置文件
	 * @param conf
	 * @param arr
	 */
	public static void parseArr(String conf, Map<Integer,Integer> arr){
		if(conf.equals("")||conf.equals(null)) {
			return;
		}
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			arr.put(i, Integer.parseInt(split[i]));
		}
	}
	
	/**
	 * 从1等级开始转换配置文件
	 * @param conf
	 * @param arr
	 */
	public static void parseArrGreaterZero(String conf, Map<Integer,Integer> arr) {
		if (conf.equals("") || conf.equals(null)) {
			return;
		}
		
		String[] split = conf.split(";");
		for (int i = 0; i < split.length; i++) {
			arr.put(i + 1, Integer.parseInt(split[i]));
		}
	}
}
