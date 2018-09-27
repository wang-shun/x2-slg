/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * buffer的注解
 * @author jiangxt
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Buffer {

	/**
	 * buffer的大小
	 * @return
	 */
	int size();
	
	/**
	 * buffer的标识(标识相同的buffer共用一条线程入库)
	 * @return
	 */
	String identify() default "";
	
}
