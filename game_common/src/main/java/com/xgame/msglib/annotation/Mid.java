package com.xgame.msglib.annotation;
/**
 *
 *2016-8-25  17:17:16
 *@author ye.yuan
 *
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mid {
	
	public int id();
}
