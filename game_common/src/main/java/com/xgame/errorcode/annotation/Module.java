package com.xgame.errorcode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 模块号
 * @author jacky.jiang
 *
 */
@Target(value=ElementType.METHOD)
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface Module {
	
	/**
	 * 模块号
	 * @return
	 */
	int module() default 0;
}
