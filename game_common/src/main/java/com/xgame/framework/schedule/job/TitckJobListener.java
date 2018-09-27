package com.xgame.framework.schedule.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.schedule.manager.ScheduleSystem;

/**
 *服务提供几种基础心跳结构    异步心跳  如果下次执行时间到了 上次任务还没执行完   会重启一个线程执行   会始终保持相对准确的执行间隔.
 *如果是心跳 必须使用这里提供的  要统一管理销毁等     如果是自定义任务时间  请使用 ScheduleSystem
 *2016-9-18  14:27:12
 *@author ye.yuan
 *
 */
@Component
public class TitckJobListener {
	
	@Autowired
	private ScheduleSystem scheduleSystem;
	
	/**
	 * 5秒
	 */
	private TitckJobParam  space5sTimerJob;
	
	/**
	 * 一小时
	 */
	private TitckJobParam  space3600sTimerJob;
	
	/**
	 * 1秒
	 */
	private TitckJobParam  space1sTimerJob;
	
	
	@PostConstruct
	public void init() {
		space5sTimerJob = new TitckJobParam();
		scheduleSystem.addJob(Space5STimerJob.class, "0/5 * * * * ?",new Object[] {"TitckJobParam", space5sTimerJob});
		
		space3600sTimerJob = new TitckJobParam();
		scheduleSystem.addJob(Space3600STimerJob.class, "0/59 * * * * ?",new Object[] {"TitckJobParam", space3600sTimerJob});
		
		space1sTimerJob = new TitckJobParam();
		scheduleSystem.addJob(Space1STimerJob.class, "* * * * * ?",new Object[] {"TitckJobParam", space1sTimerJob});
	}
	
	public TitckJobParam getSpace5sTimerJob() {
		return space5sTimerJob;
	}


	public void setSpace5sTimerJob(TitckJobParam space5sTimerJob) {
		this.space5sTimerJob = space5sTimerJob;
	}


	public TitckJobParam getSpace3600sTimerJob() {
		return space3600sTimerJob;
	}


	public void setSpace3600sTimerJob(TitckJobParam space3600sTimerJob) {
		this.space3600sTimerJob = space3600sTimerJob;
	}


	public TitckJobParam getSpace1sTimerJob() {
		return space1sTimerJob;
	}


	public void setSpace1sTimerJob(TitckJobParam space1sTimerJob) {
		this.space1sTimerJob = space1sTimerJob;
	}
	
}
