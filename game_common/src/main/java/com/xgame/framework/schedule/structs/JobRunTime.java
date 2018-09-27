package com.xgame.framework.schedule.structs;

/**  
 * @Description:job运行时间bean
 * 只读  切记改
 * @author ye.yuan
 * @date 2014年11月15日 下午11:06:47  
 *
 */
public class JobRunTime {
	
	/**job上次运行时间*/ 
	private volatile long lastRunTime;
	/**job下次运行时间*/ 
	private volatile long nextRunTime;
	
	/**
	 * @param lastTime
	 * @param nextTime
	 */
	public JobRunTime(long lastRunTime, long nextRunTime) {
		this.lastRunTime = lastRunTime;
		this.nextRunTime = nextRunTime;
	}
	
	public long getLastRunTime() {
		return lastRunTime;
	}
	
	public void setLastRunTime(long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
	
	public long getNextRunTime() {
		return nextRunTime;
	}
	
	public void setNextRunTime(long nextRunTime) {
		this.nextRunTime = nextRunTime;
	}
	
	public long getInitervalTime() {
		return lastRunTime-nextRunTime;
	}
	
}
