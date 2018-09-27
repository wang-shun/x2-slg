package com.xgame.logic.server.core.utils.scheduler.impl;

import java.util.Date;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.cache.exception.ServiceStartedException;
import com.xgame.logic.server.core.utils.scheduler.ScheduledTask;
import com.xgame.logic.server.core.utils.scheduler.Scheduler;
import com.xgame.logic.server.core.utils.scheduler.Trigger;

/**
 * 定时任务调度器
 * @author  jacky.jiang
 */
@Component
public class SimpleScheduler implements Scheduler {

	private static final Logger logger = LoggerFactory.getLogger(SimpleScheduler.class);
	
	/**线程池*/
	private FixScheduledThreadPoolExecutor executor;

	
	/**
	 * 构建定时器对象
	 * @param liveIntervals 这么多毫秒后重新检查是否有到期的任务需要执行(因为延迟队列在系统时间被调整后其到期元素是不会自动刷新的)
	 * @param coreThreadSize
	 */
	public void init(long liveIntervals, int coreThreadSize) {
		this.executor = new FixScheduledThreadPoolExecutor(coreThreadSize, liveIntervals, new ThreadFactory() {
			private final AtomicInteger sn = new AtomicInteger(0);
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "定时线程池工作者线程" + sn.incrementAndGet());
				t.setDaemon(true);
				return t;
			}
		});
	}
	
	private void assertWorking() {
//		if(!isRunning()){
//			throw new RuntimeException("定时器已停止工作...");
//		}
	}
	
	/**
	 * 停止定时器
	 */
	
	@Shutdown(order = ShutdownOrder.SCHEDULER,  desc= "系统关闭")
	public void stop() {
		this.start.set(false);
		if (executor != null) {
			executor.shutdownNow();
		}
		logger.info("关闭定时器...");
	}

	@Override
	public ScheduledFuture<?> schedule(ScheduledTask task, Trigger trigger) {
		assertWorking();
		try {
			task = new LogDecorateTask(task);
			return new SchedulingRunner(task, trigger, this.executor).schedule();
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> schedule(ScheduledTask task, Date startTime) {
		assertWorking();
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.executor.schedule(task, initialDelay, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> schedule(ScheduledTask task, String cron) {
		assertWorking();
		CronTrigger trigger = new CronTrigger(cron);
		return schedule(task, trigger);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(ScheduledTask task, Date startTime, long period) {
		assertWorking();
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.executor.scheduleAtFixedRate(task, initialDelay, period,
					TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(ScheduledTask task, long period) {
		assertWorking();
		try {
			task = new LogDecorateTask(task);
			return this.executor.scheduleAtFixedRate(task, 0, period, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> scheduleWithFixedDelay(ScheduledTask task, Date startTime, long delay) {
		assertWorking();
		long initialDelay = startTime.getTime() - System.currentTimeMillis();
		try {
			task = new LogDecorateTask(task);
			return this.executor.scheduleWithFixedDelay(task, initialDelay, delay,
					TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	@Override
	public ScheduledFuture<?> scheduleWithDelay(ScheduledTask task, long delay) {
		assertWorking();
		try {
			task = new LogDecorateTask(task);
			return this.executor.schedule(task, delay, TimeUnit.MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new TaskRejectedException("执行器不接受[" + task.getName() + "]该任务", ex);
		}
	}

	/**
	 * 用于做日志记录的任务装饰类
	 * @author frank
	 */
	private static class LogDecorateTask implements ScheduledTask {

		private ScheduledTask task;

		public LogDecorateTask(ScheduledTask task) {
			this.task = task;
		}

		public String getName() {
			return task.getName();
		}

		public void run() {
//			if (logger.isDebugEnabled()) {
//				logger.debug("任务[{}]开始运行时间[{}]", task.getName(), new Date());
//			}
			try {
				task.run();
			} catch (RuntimeException e) {
				logger.error("任务[" + task.getName() + "]在执行时出现异常!", e);
				throw e;
			}
//			if (logger.isDebugEnabled()) {
//				logger.debug("任务[{}]结束运行时间[{}]", task.getName(), new Date());
//			}
		}

	}

	/**标记是否已经开始工作*/
	private final AtomicBoolean start = new AtomicBoolean(false);
	
	@Startup(order = StartupOrder.SCHEDULE_START, desc ="定时器")
	public void start() {
		if(!start.compareAndSet(false, true)){
			throw new ServiceStartedException(this);
		}
	}

}
