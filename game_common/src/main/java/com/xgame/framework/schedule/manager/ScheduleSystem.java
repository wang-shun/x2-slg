/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */
package com.xgame.framework.schedule.manager;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.schedule.TimeJobEvent;
import com.xgame.framework.schedule.core.CronExpressionEx;
import com.xgame.framework.schedule.job.CommonCallbackJob;
import com.xgame.framework.schedule.listener.JobRunTimeListener;
import com.xgame.framework.schedule.structs.JobRunTime;
import com.xgame.framework.schedule.structs.TimeJob;
import com.xgame.utils.TimeUtils;

/**
 * @Description:任务调度管理器
 * @author ye.yuan
 * @date 2014年5月22日 下午3:55:26
 * 
 */
@Component
public class ScheduleSystem {

	private static Logger log = Logger.getLogger(ScheduleSystem.class);

	// job的group
	public static final String GAME_JOB_GROUP = "GAME_JOB_GROUP";

	// 作业调度管理器
	private Scheduler scheduler;
	
	@Autowired
	private JobRunTimeSystem jobRunTimeManager;
	
	@Autowired
	private ObjectFactory<JobRunTimeListener> jobRunTimeListeners;
	
	public ScheduleSystem() {
		
	}
 
	/**
	 * @Description:初始化
	 */
	@PostConstruct
	public void init() {
		// 初始化任务调度器
		initScheduler();
		// 启动job调度器
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.error(e);
			System.exit(0);
		}
	}

	/**
	 * @Description:初始化任务调度器
	 */
	public void initScheduler() {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
			
//			jobRunTimeManager.getJobRunTime(ActivePlayerRefreshJob.class);  例子
			// 所有的job都触发事件
			scheduler.getListenerManager().addJobListener(jobRunTimeListeners.getObject(), GroupMatcher.<JobKey> groupEquals(GAME_JOB_GROUP));
		} catch (SchedulerException e) {
			log.error(e);
			System.exit(0);
		}
	}
	
	public TimeJob fastAddJob(int time,Object listener){
		return fastAddJob(time, listener, null, null);
	}
	
	/**
	 * 不想建立job类 让我 托管的专业的进行任务调度    可以给监听者来 我给你抛过去  但是必须保证监听对象能被正确找到  如果在触发的时候监听对象不存在 导致不可预期的错 自己背锅
	 * @param time
	 * @param listener
	 * @param params
	 * @param executor 跑过去的线程    如果null  那么为同步事件
	 * @return
	 */
	public TimeJob fastAddJob(int time,Object listener,Object params,Executor executor) {
		return addJob(CommonCallbackJob.class, TimeUtils.getCron(time),new Object[]{"params", new TimeJobEvent(listener, params, executor)});
	}
	
	public TimeJob fastAddJob(String cron,Object listener,Object params,Executor executor) {
		return addJob(CommonCallbackJob.class, cron,new Object[]{"params", new TimeJobEvent(listener, params, executor)});
	}
	
	/**
	 * @Description:增加job
	 * @param jobClass
	 *            job的类
	 * @param name
	 *            job名称,对job的简单描述：如：重置玩家兑换次数，日常任务定时刷新等
	 * @param cron
	 *            cron表达式
	 */
	public TimeJob addJob(Class<? extends Job> jobClass,String cron,Object [] ... params) {
		TimeJob timeJob = new TimeJob();
		JobDetail jobDetail = createJobDetail(jobClass,timeJob);
		for(int i=0;i<params.length;i++){
			jobDetail.getJobDataMap().put(params[i][0]+"", params[i][1]);
		}
		// 作业
		addJob(jobDetail,jobClass, cron, timeJob);
		return timeJob;
	}
	
	/**
	 * 提供最基础的job添加方法    可以自定义数据结构  JobDetail 也可以 自定义 返回类型 TimeJob 的子类  
	 * @param job
	 * @param jobClass
	 * @param cron
	 * @param name
	 * @param timeJob
	 */
	public void addJob(JobDetail job,Class<? extends Job> jobClass,String cron,TimeJob timeJob) {
		CronTrigger trigger = null;
		// 作业开启后第一次运行的时间
		Date firstRunTime = null;
		// cron表达式
		CronExpression cronExpression = null;
		// 上一次运行时间
		Date preRunTime = null;
		Date now = new Date();
		try{
			cronExpression = new CronExpressionEx(cron);
			preRunTime = cronExpression.getTimeBefore(now);
			trigger = newTrigger().withIdentity(timeJob.getId()+"", GAME_JOB_GROUP).withSchedule(cronSchedule(cronExpression))// 传入自定义cron表达式实现,quantz的没有实现getTimeBefore
					.startAt(now).build();
			//加入定时任务，并且返回下一次执行的时间
			firstRunTime = scheduler.scheduleJob(job, trigger);
			} catch (ParseException e) {
				log.error(e);
			} catch (SchedulerException e) {
				log.error(e);
			}
		timeJob.setCronExpression(cronExpression);
		timeJob.setJob(job);
		timeJob.setTrigger(trigger);
		JobRunTime jobRunTime = new JobRunTime(preRunTime == null ? 0 : preRunTime.getTime(),firstRunTime == null ? 0 : firstRunTime.getTime());
		// 启动设置job的上一次和下一次运行时间
		jobRunTimeManager.setJobRunTime(jobClass, jobRunTime);
		log.info(String.format("{%s}已启动,表达式:{%s}", jobClass.getSimpleName(), trigger.getCronExpression()));
	}
	
	public JobDetail createJobDetail(Class<? extends Job> jobClass,TimeJob timeJob){
		return  newJob(jobClass).withIdentity(timeJob.getId()+"", GAME_JOB_GROUP).build();
	}

	/**
	 * @Description:停止任务调度器
	 */
	public void stopScheduler() {
		// 停止scheduler
		try {
			scheduler.standby();
			scheduler.clear();
		} catch (SchedulerException e) {
			log.error(String.format("停止任务调度器失败, exception{%s}", e));
		}
	}

}
