/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;


/**
 * 任务队列关闭
 * @author jacky.jiang
 *
 */
public class QueueTaskShutdownException extends Exception {

	private static final long serialVersionUID = -4793321266869620428L;

	public QueueTaskShutdownException(Object identify){
		super(String.format("任务队列 {%s} 已被关闭 ", identify));
	}
	
}
