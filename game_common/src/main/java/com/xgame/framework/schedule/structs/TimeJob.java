package com.xgame.framework.schedule.structs;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;

import com.xgame.utils.IDProducer;

/**
 *
 *2016-8-27  13:22:43
 *@author ye.yuan
 *
 */
public class TimeJob {
	
	private long id;
	
	private JobDetail job = null;
	
	private CronTrigger trigger = null;
	
	private CronExpression cronExpression = null;
	
	
	public TimeJob() {
		id = IDProducer.getId(0, 0);
	}

	public TimeJob(long id, JobDetail job, CronTrigger trigger,
			CronExpression cronExpression) {
		this.id = id;
		this.job = job;
		this.trigger = trigger;
		this.cronExpression = cronExpression;
	}

	public JobDetail getJob() {
		return job;
	}

	public void setJob(JobDetail job) {
		this.job = job;
	}

	public CronTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(CronTrigger trigger) {
		this.trigger = trigger;
	}

	public CronExpression getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(CronExpression cronExpression) {
		this.cronExpression = cronExpression;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
