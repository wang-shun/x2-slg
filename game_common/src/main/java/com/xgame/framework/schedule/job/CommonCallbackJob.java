package com.xgame.framework.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xgame.framework.schedule.TimeJobEvent;

/**
 *
 *2016-8-27  12:04:31
 *@author ye.yuan
 *
 */
public class CommonCallbackJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TimeJobEvent e = (TimeJobEvent)context.getJobDetail().getJobDataMap().get("params");
		e.getBus().post(e.getParam());
//		System.out.println("~~~~~~~~~~~~~CommonCallbackJob~~~~~~~~~~~~"+e.getListener());
	}
}
