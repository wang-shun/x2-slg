/**
 * 
 */
package com.xgame.logic.server.core.metric;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.utils.ThreadUtils;

/**
 * 线程池测量器
 * @author jiangxt
 *
 */
public class ThreadPoolMeter {
	
	private String poolName;
	
	private Executor executor;

	public ThreadPoolMeter(String poolName, Executor executor) {
		super();
		this.poolName = poolName;
		this.executor = executor;
	}

	public ThreadPoolMeter(Executor executor) {
		super();
		this.executor = executor;
	}

	/**
	 * 测量
	 * @return
	 */
	public MeterDescription meter(){
		MeterDescription description = new MeterDescription();
		
		if(executor instanceof ThreadPoolExecutor){
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)executor;
			
			if(StringUtils.isNotBlank(poolName)){
				description.put("线程池名称" , poolName);
			}
			description.put("当前队列上排队的任务数量", "(无法获取)");
			BlockingQueue<?> queue = ThreadUtils.getTaskQueue(threadPoolExecutor);
			if(queue != null){
				description.put("当前队列上排队的任务数量", queue.size());
			}
			
			description.put("当前池内总的线程数量", threadPoolExecutor.getPoolSize());
			description.put("当前正在执行任务的线程数", threadPoolExecutor.getActiveCount()); 
			description.put("历史执行过的任务数量", threadPoolExecutor.getCompletedTaskCount()); 
			description.put("配置的核心大小", threadPoolExecutor.getCorePoolSize()); 
			description.put("配置的最大线程数量", threadPoolExecutor.getMaximumPoolSize()); 
			description.put("历史最大峰值线程数量", threadPoolExecutor.getLargestPoolSize()); 
		} else {
			description.put(poolName, "无法内省");
		}
		
		return description;
	}
	
}
