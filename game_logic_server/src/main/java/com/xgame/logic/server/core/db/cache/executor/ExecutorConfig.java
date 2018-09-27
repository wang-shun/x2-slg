/**
 * 
 */
package com.xgame.logic.server.core.db.cache.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池配置对象
 * @author jacky.jiang
 *
 */
@Slf4j
public class ExecutorConfig {
	
	
	private static final AtomicLong sn = new AtomicLong(0);
	
	public ExecutorConfig(String name) {
		super();
		this.name = name;
	}

	/**
	 * 核心线程数
	 */
	private int coreThreadSize;
	
	/**
	 * 最大线程数
	 */
	private int maxThreadSize;
	
	/**
	 * 队列大小
	 */
	private int queueSize = Integer.MAX_VALUE;
	
	/**
	 * keepAlive的毫秒数
	 */
	private long keepAliveMillis = 30000;
	
	/**
	 * 线程池名字
	 */
	private String name = "线程池#" + sn.incrementAndGet();
	
	/**
	 * 新建一个线程池
	 * @return
	 */
	public ExecutorService newExecutor(){
		return new ThreadPoolExecutor(this.coreThreadSize, this.maxThreadSize, this.keepAliveMillis, TimeUnit.MILLISECONDS, 
				new LinkedBlockingQueue<Runnable>(queueSize),
				new NamedThreadFactory(new ThreadGroup(name), name.replace("池", "")),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						try {
							r.run();
						} catch (Exception e) {
							log.error("排队线程池执行任务异常!", e);
						}
					}
				}
			);
	}

	public int getCoreThreadSize() {
		return coreThreadSize;
	}

	public void setCoreThreadSize(int coreThreadSize) {
		this.coreThreadSize = coreThreadSize;
	}

	public int getMaxThreadSize() {
		return maxThreadSize;
	}

	public void setMaxThreadSize(int maxThreadSize) {
		this.maxThreadSize = maxThreadSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public long getKeepAliveMillis() {
		return keepAliveMillis;
	}

	public void setKeepAliveMillis(long keepAliveMillis) {
		this.keepAliveMillis = keepAliveMillis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
