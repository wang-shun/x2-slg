package com.xgame.logic.server.core.exception;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;

import com.xgame.utils.StringUtil;

/**
 *自定义异常综合管理
 *在这里  常量小写吧 大写实在难分辨  如果异常有改动  每个枚举可以自己重写  
 *2016-10-07  17:04:03
 *@author ye.yuan
 *
 */
public enum Exceptions {

	/**
	 * 不是组件类型异常
	 */
	notComponentable("not Componentable type",NotComponentableException.class),
	
	/**
	 * 找不到线程异常
	 */
	notFindThread("executor can not null",NotFindThreadException.class),
	;
	
	private Class<? extends Exception> class1;
	
	private String utf;
	
	
	private Exceptions(String utf,Class<? extends Exception> class1) {
		this.utf=utf;
		this.class1 = class1;
	}
	
	public Exception exception(Logger log) {
		Exception newInstance = null;
		try {
			Constructor<? extends Exception> constructor;
			constructor = class1.getConstructor(String.class);
			newInstance = constructor.newInstance(utf);
			log.error(StringUtil.getExceptionTrace(newInstance));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return newInstance;
	}
}
