/**
 * 
 */
package com.xgame.logic.server.core.db.cache.executor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xgame.logic.server.core.db.cache.exception.QueueTaskLimitedException;
import com.xgame.logic.server.core.db.cache.exception.QueueTaskShutdownException;


/**
 * 有任务数量限制的
 * @author jacky
 *
 */
public class LimitedIdentifyQueueExecutor implements IdentifyExecutor{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/** {key,队列任务}*/
	protected final ConcurrentMap<Object, QueueTask> identify_tasks = new ConcurrentHashMap<Object, QueueTask>();
	
	/** 主工作线程池*/
	protected ExecutorService mainExecutor;
	
	/** 每个id上排队任务的数量*/
	private int taskLimit;
	
	/**
	 * 构建线程池
	 * @param executorConfig
	 * @param taskLimit 每个id上排队任务的数量(<=0表示没有限制)
	 */
	public LimitedIdentifyQueueExecutor(ExecutorConfig executorConfig, int taskLimit){
		this.mainExecutor = executorConfig.newExecutor();
		this.taskLimit = taskLimit;
	}
	
	@Override
	public void execute(Object identify, Runnable runnable) throws QueueTaskLimitedException, QueueTaskShutdownException {
		QueueTask queueTask = identify_tasks.get(identify);
		if(queueTask == null) {
			queueTask = new QueueTask(identify, this.taskLimit);
			identify_tasks.putIfAbsent(identify, queueTask);
			queueTask = identify_tasks.get(identify);
		}
		
		boolean submit = queueTask.offer(runnable);
		
		if(submit) {
			mainExecutor.execute(queueTask);
		}
	}

	@Override
	public void shutdown(boolean rightNow) {
		if(rightNow) {
			for(QueueTask queueTask : identify_tasks.values()){
				queueTask.shundown();
			}
			identify_tasks.clear();
			mainExecutor.shutdownNow();
		} else {
			for(QueueTask queueTask : identify_tasks.values()){
				queueTask.shundown();
			}
			identify_tasks.clear();
			mainExecutor.shutdown();
			
			for(int i = 0; i < 30; i++){
				try {
					if(mainExecutor.awaitTermination(1, TimeUnit.SECONDS)){
						break;
					}
				} catch (InterruptedException e) {
					break;
				}
			}
			
		}
	}

	@Override
	public void shudown(final Object identify, boolean rightNow) {
		
		//立即关闭后不再接受任务
		if(rightNow){
			QueueTask shutdownTask = identify_tasks.remove(identify);
			if(shutdownTask != null){
				shutdownTask.shundown();
			}
			return;
		}
		
		QueueTask queueTask = identify_tasks.get(identify);
		if(queueTask != null){
			
			//平滑关闭如果期间还有提交继续执行
			final CountDownLatch countDownLatch = new CountDownLatch(1);
			
			Runnable runnable = new ShutdownTask() {
				@Override
				public void run() {
					identify_tasks.remove(identify);
					countDownLatch.countDown();
				}
			};
			
			
			try {
				if(queueTask.offer(runnable)){
					mainExecutor.execute(queueTask);
				}
				
				try {
					countDownLatch.await(2, TimeUnit.MINUTES);//最多等待两分钟
				} catch (InterruptedException e) {
					logger.error("排队线程关闭时被打断", e);
				}
				
			} catch (QueueTaskLimitedException e) {
				logger.error("关闭排队线程池异常", e);
			} catch (QueueTaskShutdownException e) {
				logger.error("关闭排队线程池异常", e);
			}
			
		}
	}
	
	//关闭任务
	static interface ShutdownTask extends Runnable {
		
	}

}
