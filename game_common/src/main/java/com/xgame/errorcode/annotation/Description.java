package com.xgame.errorcode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 描述
 * @author jacky.jiang
 *
 */
@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface Description {
	
	/**
	 * 描述
	 * @return
	 */
	String desc();
	
}
