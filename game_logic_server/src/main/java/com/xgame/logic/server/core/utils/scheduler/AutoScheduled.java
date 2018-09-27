/**
 * 
 */
package com.xgame.logic.server.core.utils.scheduler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动定时,本注解直接申明在接口上
 * @see AutoSchedulingProxy
 * @author jacky.jiang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoScheduled {
	
	/**
	 * 定时名
	 * @return
	 */
	String name();
	
	/**
	 * cron表达式
	 * @return
	 */
	String cron();

	/**
	 * 目标类
	 * @return
	 */
	Class<?> target();
	
	/**
	 * 方法名
	 * @return
	 */
	String method();
	
}
