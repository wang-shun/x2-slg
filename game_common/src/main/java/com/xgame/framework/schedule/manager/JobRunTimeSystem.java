package com.xgame.framework.schedule.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import com.xgame.framework.schedule.structs.JobRunTime;


/**  
 * @Description:job运行时间统一管理
 * 只读  不要改
 * @author ye.yuan
 * @date 2014年11月15日 下午11:11:22  
 *
 */
@Component
public class JobRunTimeSystem {
	
	/**job运行时间map*/ 
	private Map<Class<? extends Job>, JobRunTime> jobRunTimes = new ConcurrentHashMap<>();
	
	/**
	 * @Description:获取指定job的运行时间封装
	 * @param jobClzz
	 * @return
	 */	
	public JobRunTime getJobRunTime(Class<? extends Job> jobClzz) {
		return jobRunTimes.get(jobClzz);
	}
	
	/**
	 * @Description:设置job的运行时间
	 * @param jobClzz
	 * @param jobRunTime
	 */
	public void setJobRunTime(Class<? extends Job> jobClzz, JobRunTime jobRunTime) {
		jobRunTimes.put(jobClzz, jobRunTime);
	}
}
