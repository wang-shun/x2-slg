package com.xgame.logic.server.core.utils.scheduler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 计划任务触发条件声明注释(使用 Cron 表达式)
 * 本注解已废弃使用 {@link AutoScheduled} 替代
 * @author jacky.jiang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

	/** 任务名 */
	String name();

	/** 表达式值 */
	String value();
	
	/** 表达式值类型 */
	ValueType type() default ValueType.EXPRESSION;
	
	/** 当无法获取表达式值时使用的默认值 */
	String defaultValue() default "";
	
}
