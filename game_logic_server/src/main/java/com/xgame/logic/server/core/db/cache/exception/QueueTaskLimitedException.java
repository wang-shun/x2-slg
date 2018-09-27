/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;


/**
 * 任务达到上线
 * @author jacky.jiang
 *
 */
public class QueueTaskLimitedException extends Exception {

	private static final long serialVersionUID = -4793321266869620428L;

	public QueueTaskLimitedException(Object identify, int taskLimit){
		super(String.format("任务队列 [{0}]长度限制 [{1}]" , identify, taskLimit));
	}
	
}
