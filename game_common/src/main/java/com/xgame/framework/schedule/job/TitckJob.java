package com.xgame.framework.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 *2016-9-18  14:29:00
 *@author ye.yuan
 *
 */
public abstract class TitckJob implements Job{
	
	TitckJobParam param;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		param = (TitckJobParam)context.getJobDetail().getJobDataMap().get("TitckJobParam");
		action();
	}
	
	public abstract void action();

}
