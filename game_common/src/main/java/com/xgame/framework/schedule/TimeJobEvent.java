package com.xgame.framework.schedule;

import java.util.concurrent.Executor;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.xgame.framework.schedule.structs.JobRunTime;
import com.xgame.framework.schedule.structs.TimeJob;

/**
 *
 *2016-8-27  12:14:49
 *@author ye.yuan
 *
 */
@SuppressWarnings("unchecked")
public class TimeJobEvent {
	
	private JobRunTime jobRunTime;
	
	private Object param;
	
	private Executor executor;
	
	private Object listener;
	
	private EventBus bus;
	
	private TimeJob job;
	
	public TimeJobEvent(Object listener,Object param,Executor executor) {
		this.param = param;
		this.executor=executor;
		this.listener=listener;
		if(executor!=null){
			bus=new AsyncEventBus(executor);	
		}else{
			bus=new EventBus();	
		}
		bus.register(listener);
	}

	public JobRunTime getJobRunTime() {
		return jobRunTime;
	}
	
	public <T> T getParam() {
		return (T)param;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public Object getListener() {
		return listener;
	}

	public EventBus getBus() {
		return bus;
	}

	public void setBus(EventBus bus) {
		this.bus = bus;
	}

	public TimeJob getJob() {
		return job;
	}

	public void setJob(TimeJob job) {
		this.job = job;
	}

}
