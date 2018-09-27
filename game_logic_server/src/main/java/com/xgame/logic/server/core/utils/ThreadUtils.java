/**
 * 
 */
package com.xgame.logic.server.core.utils;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程工具类
 * @author jianxt
 *
 */
public abstract class ThreadUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);
	
	/**
	 * 关闭线程池
	 * @param threadPool 需要关闭的线程池
	 * @param shutdownNow true-立即关闭放弃当前执行的任务  false-等待所提交的任务都完成后再最初
	 */
	public static void shundownThreadPool(ExecutorService threadPool, boolean shutdownNow){
		if(shutdownNow){
			try {
				threadPool.shutdownNow();
			}catch (Exception e) {
				if(!(e instanceof InterruptedException)){
					logger.error("关闭线程池时出错!", e);
				}
			}
		} else {
			threadPool.shutdown();
			boolean taskComplete = false;
			for(int i = 0; i < 30; i++){//最多等待30秒
				
				logger.info("正在第 [{}] 次尝试关闭线程池!", i+1);
				
				try {
					taskComplete = threadPool.awaitTermination(1, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					if(!taskComplete){
						continue;
					}
				}
				
				if(taskComplete){
					break;
				} else {
					if(threadPool instanceof ThreadPoolExecutor){
						Queue<?> taskQueue = getTaskQueue((ThreadPoolExecutor)threadPool);
						if(taskQueue != null){
							logger.info("当前正在关闭的线程池尚有 [{}] 个任务排队等待处理!", taskQueue.size());
						}
					}
					
				}
			}
			
			if(!taskComplete){
				logger.info("线程池非正常退出!");
			} else {
				logger.info("线程池正常退出!");
			}
		}
	}
	
	
	
	/**
	 * 获取线程池的任务队列
	 * @param threadPoolExecutor
	 * @return
	 */
	public static BlockingQueue<?> getTaskQueue(ThreadPoolExecutor threadPoolExecutor){
		BlockingQueue<?> queue = null;
		try {
			queue = threadPoolExecutor.getQueue();
		} catch (Exception e) {
			try {
				Field field = ThreadPoolExecutor.class.getDeclaredField("workQueue");
				field.setAccessible(true);
				queue = (BlockingQueue<?>)field.get(threadPoolExecutor);
			} catch (Exception e2) {
			}
		}
		return queue;
	}
	
	public static void main(String[] args) {
		ExecutorService executr = Executors.newCachedThreadPool();
		
		executr.execute(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < 20; i++){
					try {
						Thread.sleep(1000);
						System.out.println(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		shundownThreadPool(executr, false);
	}
	

}
