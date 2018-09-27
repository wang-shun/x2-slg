package com.message;
	

<#list fields as field>
import ${field.handlerName};
import ${field.messageName};
</#list>

<#list fields2 as field>
import ${field.messageName};
</#list>
/**
 *
 *2016-11-28  21:36:00
 *@author ye.yuan
 *
 */
public enum MessageEnum {

	/**
	 * 客户端请求消息和处理器
	 */
	<#list fields as field>
	Req${field.funcId?c}${field.id?c}(${field.funcId?c},${field.id?c},${field.handlerSimpleName}.class,${field.messageSimpleName}.class),
	</#list>
	
	
	/**
	 * 发送给客户端的消息
	 */
	<#list fields2 as field>
	Res${field.funcId?c}${field.id?c}(${field.funcId?c},${field.id?c},${field.messageSimpleName}.class),
	</#list>
	;
	
	private MessageEnum(int functionId,int id,Class<?> handlerClass,Class<?> messageClass) {}
	private MessageEnum(int functionId,int id,Class<?> messageClass) {}
}
