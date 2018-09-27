package com.xgame.logic.server.gm.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *2016-8-23  12:27:38
 *@author ye.yuan
 *
 */
@Target({java.lang.annotation.ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface Admin {

	public abstract int level(); 
	 
}
