/**
 * 
 */
package com.xgame.logic.server.core.db.cache.factory;


/**
 * spring相关的工具类
 * @author jacky
 *
 */
abstract class SpringHelper {

	/**
	 * 根据类名生成bean名 
	 * @param beanClass
	 * @return
	 */
	public static String generateBeanName(Class<?> beanClass){
		String beanClassName = beanClass.getName();
//		int lastDot = beanClassName.lastIndexOf(".");
//		beanClassName = beanClassName.substring(lastDot + 1);
		char firstChar = beanClassName.charAt(0);
		StringBuilder builder = new StringBuilder().append(Character.toLowerCase(firstChar));
		if(beanClassName.length() > 1){
			builder.append(beanClassName.substring(1));
		}
		return builder.toString();
	}
	
}
