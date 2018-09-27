/**
 * 
 */
package com.xgame.logic.server.core.db.cache.executor;

import com.xgame.logic.server.core.db.cache.exception.QueueTaskLimitedException;
import com.xgame.logic.server.core.db.cache.exception.QueueTaskShutdownException;



/**
 * 可标识的执行者
 * @author jacky
 *
 */
public interface IdentifyExecutor {
	
	/**
	 * 执行一个任务
	 * @param identify 每个标识的任务都排一个队列
	 * @param runnable
	 */
	void execute(Object identify, Runnable runnable) throws QueueTaskLimitedException, QueueTaskShutdownException;
	
	/**
	 * 关闭线程池
	 * @param rightNow rightNow true-立即关闭不再接收入队请求
	 */
	void shutdown(boolean rightNow);
	
	/**
	 * 关闭指定标识的任务队列(关闭后在关闭任务没有执行完之前可以继续提交并执行任务，直到执行完毕队列删除)
	 * @param identify
	 * @param rightNow true-立即关闭不再接收入队请求
	 */
	void shudown(Object identify, boolean rightNow);
	
}
