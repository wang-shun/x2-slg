package com.xgame.utils;

import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * spring上下文
 * @author jacky.jiang
 *
 */
public abstract class AppliactionContext {

	// 
	private static ApplicationContext context;
	
	public static void init(ApplicationContext context) {
		AppliactionContext.context = context;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return (T)context.getBean(clazz);
	}
	
	public static <T> Map<String, T> getBeansForType(Class<T> clazz) {
		return context.getBeansOfType(clazz);
	}
}
