package com.xgame.logic.server.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取class信息
 * @author jacky.jiang
 *
 */
@Slf4j
public class ClassNameUtils {
	
	@SuppressWarnings("rawtypes")
	private static Map<String, Class> nameClass = new ConcurrentHashMap<String, Class>();

	@SuppressWarnings("rawtypes")
	public static void addClass(String name, Class clazz) {
		nameClass.put(name, clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String name) {
		Class clazz = nameClass.get(name);
		if(clazz == null) {
			try {
				clazz = Class.forName(name);
			} catch (ClassNotFoundException e) {
				log.error("反射获取class异常:", e);
			}
			nameClass.put(name, clazz);
		}
		return clazz;
	}
}
