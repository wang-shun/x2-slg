package com.log.dispatcher.structs;

import java.lang.reflect.Field;

import com.google.common.eventbus.EventBus;

/**
 *日志事件
 *
 *@author ye.yuan
 *@date 2016-7-08  18:18:07
 */
public class LogEvent {
	
	/**
	 * 行为id
	 */
	private int actionId;
	
	/**
	 * 日志本类
	 */
	private Class<?> logClass;
	
	/**
	 * 自定义订阅参数类
	 */
	private Object param;
	
	/**
	 * 监听者
	 */
	private Object listerer;
	
	/**
	 * 本次订阅事件调度
	 */
	private EventBus bus;
	
	/**
	 * 初始化就找出@LogInfo注解的字段  触发的时候快速赋值   
	 */
	private Field logInfoField;
	
	
	public LogEvent(int actionId, Class<?> logClass, Object param,Object listerer) {
		this.actionId = actionId;
		this.logClass = logClass;
		this.listerer = listerer;
		this.param=param;
	}


	public Object getListerer() {
		return listerer;
	}


	public void setListerer(Object listerer) {
		this.listerer = listerer;
	}


	public int getActionId() {
		return actionId;
	}


	public void setActionId(int actionId) {
		this.actionId = actionId;
	}


	public Class<?> getLogClass() {
		return logClass;
	}


	public void setLogClass(Class<?> logClass) {
		this.logClass = logClass;
	}


	public Object getParam() {
		return param;
	}


	public void setParam(Object subscribe) {
		this.param = subscribe;
	}


	public EventBus getBus() {
		return bus;
	}


	public void setBus(EventBus bus) {
		this.bus = bus;
	}


	public Field getLogInfoField() {
		return logInfoField;
	}


	public void setLogInfoField(Field logInfoField) {
		this.logInfoField = logInfoField;
	}
}
