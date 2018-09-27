/**
 * 
 */
package com.xgame.logic.server.core.db.cache.executor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgame.logic.server.core.db.cache.exception.QueueTaskLimitedException;
import com.xgame.logic.server.core.db.cache.exception.QueueTaskShutdownException;
import com.xgame.logic.server.core.db.cache.executor.LimitedIdentifyQueueExecutor.ShutdownTask;

/**
 * 排队的任务队列
 * @author jacky
 *
 */
public class QueueTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueTask.class);
	
	private static final AtomicLong sn = new AtomicLong(0);
	
	//标识
	protected Object identify = "任务队列#" + sn.incrementAndGet();
	
	//锁
	private final Lock lock = new ReentrantLock();
	
	//是否在运行中
	private volatile boolean finish = true;
	
	//任务数量限制
	private final int taskLimit;
	
	//是否被关闭了
	private volatile boolean shutdown = false;
	
	public QueueTask(Object identify, int taskLimit) {
		super();
		this.identify = identify;
		this.taskLimit = taskLimit;
	}
	
	/**
	 * 将任务加入的排队的任务中
	 * @param runnable
	 * @return true-队列任务当前没有运行需要重新提交队列任务  false-队列已经在运行中不需再次提交任务
	 */
	public boolean offer(Runnable runnable) throws QueueTaskLimitedException, QueueTaskShutdownException{
		
		lock.lock();
		try {
			
			if(shutdown){//已经是很小的概率了
				throw new QueueTaskShutdownException(this.identify);
			}
			
			if(taskLimit > 0 && !(runnable instanceof ShutdownTask)){//关闭任务一定能提交
				if(taskQueue.size() >= taskLimit){
					throw new QueueTaskLimitedException(this.identify, this.taskLimit);
				}
			}
			
			taskQueue.offer(runnable);
			
			if(finish) {
				finish = false;
				return true;
			}
		} finally {
			lock.unlock();
		}
		
		return false;
	}

	final Queue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
	
	@Override
	public void run() {
		//关闭任务
		Runnable shutdownTask = null;
		while(true){
			Runnable task;
			lock.lock();
			try {
				task = taskQueue.poll();
				if(task == null){
					finish = true;
					if(shutdownTask != null){//队列消耗完毕没有任务了最后才关闭队列
						shutdownTask.run();
						shutdown = true;
					}
					break;
				} 
				
				//如果是关闭任务则放到队列最后去执行这样尽量延迟队列的关闭时间
				if (task instanceof ShutdownTask) {
					shutdownTask = task;
					continue;
				}
			} finally {
				lock.unlock();
			}
			
			try {
				task.run();
			} catch (Exception e) {
				logger.error(String.format("排队线程池任务 {%s} 执行异常!", identify.toString(), e));
			}
		}
	}
	
	/**
	 * 队列任务是否已经关闭
	 * @return
	 */
	public boolean isShutdown(){
		return this.shutdown;
	}
	
	/**
	 * 关闭队列(友元访问)
	 */
	void shundown() {
		lock.lock();
		try {
			this.shutdown = true;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 返回队列上带执行任务的大小
	 * @return
	 */
	public int size(){
		return this.taskQueue.size();
	}
}
