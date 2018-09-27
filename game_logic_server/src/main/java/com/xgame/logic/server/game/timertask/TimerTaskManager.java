package com.xgame.logic.server.game.timertask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.ThreadUtils;
import com.xgame.logic.server.core.utils.scheduler.ScheduledTask;
import com.xgame.logic.server.core.utils.scheduler.Scheduler;
import com.xgame.logic.server.core.utils.scheduler.impl.FixTimeDelayQueue;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.bean.TimerBean;
import com.xgame.logic.server.game.timertask.constant.TimerTaskConstant;
import com.xgame.logic.server.game.timertask.constant.TimerTaskExecutorConfig;
import com.xgame.logic.server.game.timertask.converter.TimerTaskConverter;
import com.xgame.logic.server.game.timertask.entity.ExecutorConfig;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.timertask.entity.system.ISystemTask;
import com.xgame.logic.server.game.timertask.entity.system.SystemTimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;
import com.xgame.logic.server.game.timertask.message.ResAllTimerMessage;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldMarchManager;

/**
 * 事件执行队列管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class TimerTaskManager extends CacheProxy<TimerTaskData> {

	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private WorldMarchManager worldMarchManager;
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	private Scheduler scheduler;
	
	/**
	 * 延迟队列
	 */
	private FixTimeDelayQueue<Delayed> delayQueue = new FixTimeDelayQueue<>(3000);
	
	/**
	 * 线程池任务执行器
	 */
	private ExecutorService exector;
	
	/**
	 * 任务线程
	 */
	private Thread taskThread;
	
	/**
	 * 任务队列
	 */
	private Lock lock = new ReentrantLock();
	
	@PostConstruct
	private void init() {
		ExecutorConfig executorConfig = new ExecutorConfig(TimerTaskExecutorConfig.TIMER_TASK_EXECUTOR_NAME);
		executorConfig.setCoreThreadSize(TimerTaskExecutorConfig.CORE_THREAD_SIZE);
		executorConfig.setMaxThreadSize(TimerTaskExecutorConfig.MAX_THREAD_SIZE);
		exector = executorConfig.newExecutor();
		
		if(taskThread != null) {
			taskThread.interrupt();
		}
		
		taskThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Delayed delayed = delayQueue.take();
						if(delayed != null) {
							if(delayed instanceof TimerTaskData) {
								lock.lock();
								try {
									TimerTaskData timerTaskData = (TimerTaskData)delayed;
									ITimerTask<?> timer  = TimerTaskHolder.getTimerTask(timerTaskData.getQueueId());
									if(timer != null) {
										Player player = playerManager.getPlayer(timerTaskData.getRoleId());
										if(player == null) {
											return;
										}
										
										exector.execute(timer.getRunnable(player, timerTaskData));
									}
								} finally {
									lock.unlock();
								}
							} else if(delayed instanceof SystemTimerTaskData) {
								lock.lock();
								try {
									SystemTimerTaskData timerTaskData = (SystemTimerTaskData)delayed;
									ISystemTask<?> systemTask  = SystemTimerTaskHolder.getTimerTask(timerTaskData.getQueueId());
									if(systemTask != null) {
										exector.execute(systemTask.getRunnable(timerTaskData));
									}
								} finally {
									lock.unlock();
								}
							}
						}
					} catch(Exception e) {
						log.error("定时任务队列异常：",e);
					}
				}
			}
		});
		
		taskThread.setDaemon(true);
		taskThread.start();
	}
	
	/**
	 * 添加定时任务
	 * @param player
	 * @param data
	 * @param runnable
	 * @return
	 */
	public long addTimerTask(Player player, TimerTaskData data, Runnable runnable) {
		lock.lock();
		try {
			InjectorUtil.getInjector().dbCacheService.create(data);
			delayQueue.add(data);
			player.roleInfo().getTimerTaskMap().put(data.getTaskId(), data.getTaskId());
			InjectorUtil.getInjector().dbCacheService.update(player);
			return data.getTaskId();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 移除定时任务
	 * @param player
	 * @param data
	 */
	public void removeTimerTask(Player player, TimerTaskData data) {
		lock.lock();
		try {
			InjectorUtil.getInjector().dbCacheService.delete(data);
			delayQueue.remove(data);
			
			player.roleInfo().getTimerTaskMap().remove(data.getTaskId());
			InjectorUtil.getInjector().dbCacheService.update(player);
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 移除定时任务
	 * @param player
	 * @param timerTaskId
	 */
	public void removeTimerTask(Player player, long timerTaskId) {
		lock.lock();
		try {
			TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, timerTaskId);
			if(timerTaskData != null) {
				InjectorUtil.getInjector().dbCacheService.delete(timerTaskData);
				player.roleInfo().getTimerTaskMap().remove(timerTaskData.getTaskId());
				delayQueue.remove(timerTaskData);
				
				// 更新时间队列
				InjectorUtil.getInjector().dbCacheService.update(player);
			}
		} finally {
			lock.unlock();
		}
	}
	
	
	/**
	 * 添加定时任务
	 * @param player
	 * @param data
	 * @param runnable
	 * @return
	 */
	public long addSystemTimerTask(SystemTimerTaskData data) {
		lock.lock();
		try {
			InjectorUtil.getInjector().dbCacheService.create(data);
			delayQueue.add(data);
			return data.getTaskId();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 移除定时任务
	 * @param player
	 * @param data
	 */
	public void removeSystemTimerTask(SystemTimerTaskData data) {
		lock.lock();
		try {
			SystemTimerTaskData systemTimerTaskData = InjectorUtil.getInjector().dbCacheService.get(SystemTimerTaskData.class, data.getId());
			if(systemTimerTaskData != null) {
				InjectorUtil.getInjector().dbCacheService.delete(systemTimerTaskData);
				delayQueue.remove(systemTimerTaskData);
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 重置时间队列
	 * @param player
	 * @param data
	 * @param runnable
	 */
	public void resetSystemTimerTask(SystemTimerTaskData data) {
		lock.lock();
		try {
			this.delayQueue.remove(data);
			this.delayQueue.add(data);
			InjectorUtil.getInjector().dbCacheService.update(data);
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 重置任务
	 * @param player
	 * @param data
	 * @param runnable
	 */
	public void resetTimerTask(Player player, TimerTaskData data, int currentTime, int fixTime) {
		lock.lock();
		try {
			// 更新时间队列
			// 根据当前时间,执行周期 修改结束时间
			if(currentTime > 0) {
				// 需要结束的队列
				if (fixTime <= 0) {
					data.setTaskTime(0);
					data.setTriggerTime(0);
				// 等待时间结束队列
				} else {
					data.setTaskTime(fixTime);
					data.setTriggerTime(currentTime + fixTime);	
				}
			// 修改触发结束时间
			} else {
				data.setTriggerTime(data.getTriggerTime() + fixTime);
			}
			
			this.delayQueue.remove(data);
			this.delayQueue.add(data);
		
			InjectorUtil.getInjector().dbCacheService.update(data);
		} finally {
			lock.unlock();
		}
	}
	
	@Startup(order = StartupOrder.TIMER_START, desc = "定时任务管理器")
	public void start() {
		//初始化行军队列
		worldMarchManager.init();

		// 添加到队列
		List<TimerTaskData> objs = InjectorUtil.getInjector().redisClient.hvals(TimerTaskData.class);
		if(objs != null && !objs.isEmpty()) {
			for(TimerTaskData timerTaskData : objs) {
				ITimerTask<?> timer  = TimerTaskHolder.getTimerTask(timerTaskData.getQueueId());
				if(timer != null) {
					Player player = playerManager.getPlayer(timerTaskData.getRoleId());
					if(player == null) {
						return;
					}
					
					this.delayQueue.add(timerTaskData);
					this.add(timerTaskData);
				}
			}
		}
		
		// 一分钟之后处理战斗异常
		scheduler.scheduleWithDelay(new ScheduledTask() {
			@Override
			public void run() {
				// 处理精灵异常
				try  {
					spriteManager.dealExceptionSpriteInfo();
				} catch(Exception e ) {
					log.error("处理精灵异常:", e);
				}
			}
			
			@Override
			public String getName() {
				return "启动之后执行处理";
			}
		}, 60 * 1000);
		
		// 每隔五分钟处理行军队列卡住的情况
		scheduler.scheduleAtFixedRate(new ScheduledTask() {
			@Override
			public void run() {
				// 处理行军异常
				try  {
					worldMarchManager.dealExceptionMarch();
				} catch(Exception e ) {
					log.error("处理行军队列异常:", e);
				}
			}
			
			@Override
			public String getName() {
				return "处理行军队列异常。";
			}
		}, 30  * 1000);

	}

	public TimerTaskData getTimerTaskData(long taskId) {
		return InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, taskId);
	}
	
	public void addTimerTaskCache(TimerTaskData timerTaskData) {
		InjectorUtil.getInjector().dbCacheService.create(timerTaskData);
	}

	@Override
	public Class<?> getProxyClass() {
		return TimerTaskData.class;
	}

	@Shutdown(order = ShutdownOrder.GAME_TIMER, desc = "定时任务关闭")
	public void stop() {
		//  关闭线程
		if(taskThread != null) {
			taskThread.interrupt();
		}
		
		//系统关闭线程池
		if(exector != null) {
			ThreadUtils.shundownThreadPool(exector, false);
		}
	}
	
	public void sendTimerTask(Player player, List<TimerTaskData> timerTaskDataList) {
		List<TimerBean> sendTimeList = new ArrayList<>();
		if(timerTaskDataList != null) {
			for(TimerTaskData timerTaskData : timerTaskDataList) {
				if(timerTaskData.getQueueId() < TimerTaskConstant.TIMER_TASK_INNER_QUEUE_ID) {
					sendTimeList.add(TimerTaskConverter.toTimerBean(player, timerTaskData));
				}
			}
		}
		
		ResAllTimerMessage resAllTimerMessage = new ResAllTimerMessage();
		resAllTimerMessage.timers = sendTimeList;
		player.send(resAllTimerMessage);
	}
	
	/**
	 * 发送玩家身上的timertask
	 * @param player
	 */
	public void sendAllTimerTask(Player player) {
		List<TimerTaskData> timerTaskDatas = new ArrayList<TimerTaskData>();
		Iterator<Long> iterator = player.roleInfo().getTimerTaskMap().values().iterator();
		while (iterator.hasNext()) {
			Long id =  iterator.next();
			TimerTaskData timerTaskData = InjectorUtil.getInjector().timerTaskManager.getTimerTaskData(id);
			if(timerTaskData != null) {
				timerTaskDatas.add(timerTaskData);
			}
		}
		
		// 发送
		sendTimerTask(player, timerTaskDatas);
	}
}
