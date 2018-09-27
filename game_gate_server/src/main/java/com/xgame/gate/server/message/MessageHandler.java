package com.xgame.gate.server.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.framework.network.server.ClientMessage;

 
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Scope("prototype")
@Component
public @interface MessageHandler {
	ClientMessage value();

	boolean sync() default false;
}
