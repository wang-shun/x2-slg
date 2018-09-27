package com.xgame.framework.executer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 *游戏线程池
 *
 *2017-2-04  10:02:59
 *@author ye.yuan
 *
 */
@Slf4j
public class GameSingleExecutorService {
	
	/**
	 * 线程池对象
	 */
	ExecutorService executor;

	/**
	 * 当前线程id
	 */
	private long currentThreadId;
	
	public GameSingleExecutorService(String name) {
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				
				Thread t = new Thread(r, name);
				
				t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){  
				      @Override  
				      public void uncaughtException(Thread t, Throwable e) {  
				    	  e.printStackTrace();
				      }  
				   }); 
				//替换当前线程id
				currentThreadId=t.getId();
				return t;
			}
		});
	}
	
	public GameSingleExecutorService(String name,AtomicInteger index) {
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				
				Thread t = new Thread(r, name + "-"
						+ index.incrementAndGet());
				
				t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){  
				      @Override  
				      public void uncaughtException(Thread t, Throwable e) {  
				    	  e.printStackTrace();
				      }  
				   }); 
				log.debug("{}", index);
				currentThreadId=t.getId();
				return t;
			}
		});
	}
	
	/**
	 * 验证是否在当前线程
	 * @param threadId
	 * @return
	 */
	public boolean isCurrentThread(long threadId) {
		return currentThreadId == threadId;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public long getCurrentThreadId() {
		return currentThreadId;
	}

	public void setCurrentThreadId(long currentThreadId) {
		this.currentThreadId = currentThreadId;
	}
}
