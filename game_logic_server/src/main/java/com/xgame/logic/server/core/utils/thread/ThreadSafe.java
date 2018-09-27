package com.xgame.logic.server.core.utils.thread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *2016-10-06  12:03:36
 *@author ye.yuan
 *
 */
@Target(value={ElementType.METHOD,ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
public @interface ThreadSafe {

}
