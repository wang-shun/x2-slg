/**
 * Copyright (c) 2014, http://www.moloong.com/ All Rights Reserved. 
 * 
 */  
package com.xgame.framework.schedule.listener;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.framework.schedule.manager.JobRunTimeSystem;
import com.xgame.framework.schedule.structs.JobRunTime;

/**  
 * @Description:作业运行时间监听器，主要用于记录job的上次和下次运行时间
 * @author ye.yuan
 * @date 2014年11月19日 下午2:36:22  
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JobRunTimeListener extends JobListenerSupport{
	
	@Autowired
	JobRunTimeSystem jobRunTimeManager;
	
	@Override
	public String getName() {
		return "JobRunTimeListener";
	}

	/**
	 * @Description 任务执行之前,记录任务上一次和下一次执行时间
	 * @param context 任务执行上下文
	 * 
	 * @date 2015年9月23日 下午2:14:28
	 */
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		super.jobToBeExecuted(context);
		//job的运行时间
		JobRunTime jobRunTime = jobRunTimeManager.getJobRunTime(context.getJobDetail().getJobClass());
		//这次执行的触发时间
		Date fireTime = context.getFireTime();
		//下一次执行的时间
		Date nextFireTime = context.getNextFireTime();
		//设置上一次执行的时间
		jobRunTime.setLastRunTime(fireTime == null? 0 : fireTime.getTime());
		//设置下一次执行的时间
		jobRunTime.setNextRunTime(nextFireTime == null? 0 : nextFireTime.getTime());
	}

}
