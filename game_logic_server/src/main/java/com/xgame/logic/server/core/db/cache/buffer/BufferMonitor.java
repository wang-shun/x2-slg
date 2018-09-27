/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * buffer 监控
 * @author jacky.jiang
 *
 */
public class BufferMonitor implements BufferMonitorMXBean{
	
	/**是否开启*/
	private boolean open = true;
	
	/**buffer管理的实体类*/
	private String identify;
	
	/**观察窗口大小(100个入库事务)*/
	private int transWindowSize = 100;
	
	/**当前计数器*/
	private int count = 0;
	
	/**观察窗口类累计等待处理时间(毫秒)*/
	private long totalWaitTime;
	
	/**观察窗口类累计处理时间(毫秒)*/
	private long totalExeTime;
	
	/**累计接收到事务事*/
	private long totalTrans;
	
	/**当前正在进行中的事务*/
	private Transaction current;
	
	/**最大平均等待时间(毫秒)*/
	private double maxAvgWaitTime;
	
	/**最大平均等待时间(毫秒)刷新的那个时间点*/
	private Date maxAvgWaitOccurTime = new Date();
	
	/**最大平均执行时间(毫秒)*/
	private double maxAvgExeTime;
	
	/**最大平均执行时间(毫秒)刷新的那个时间点*/
	private Date maxAvgExeOccurTime = new Date();
	
	/**当前的tps*/
	private int tps;
	
	/**历史最大的tps*/
	private int maxTps;
	
	/**历史最大的tps发生时间*/
	private Date maxTpsOccurTime = new Date();
	
	/**当前的时间窗口序号*/
	private long timeWindow;
	
	/**时间窗口大小(毫秒)*/
	private long timeWindowSize = 1000;
	
	public BufferMonitor(Object identify) {
		super();
		this.identify = String.valueOf(identify);
	}

	/**
	 * 开始一个事务
	 * @return
	 */
	public Transaction beginTransaction(){
		Transaction t = new Transaction();
		this.current = t;
		return t;
	}
	
	/**
	 * 结束一个事务
	 * @param transaction
	 * @return true-有一项指标超越了最大
	 */
	public boolean endTransaction(Transaction transaction){
		
		boolean exceed = false;
		
		if(current == transaction){//事务相同才纳入计算
			
			if(count >= transWindowSize){
				
				double thisAvgWaitTime = getAvgWaitTime();
				if(thisAvgWaitTime > maxAvgWaitTime){
					exceed = maxAvgWaitTime > 0;
					maxAvgWaitTime = thisAvgWaitTime;
					maxAvgWaitOccurTime = new Date();
					exceed = true;
				}
				double thisAvgExeTime = getAvgExeTime();
				if(thisAvgExeTime > maxAvgExeTime){
					exceed = maxAvgExeTime > 0;
					maxAvgExeTime = thisAvgExeTime;
					maxAvgExeOccurTime = new Date();
				}
				
				count = 1;
				this.totalWaitTime = transaction.waitTime;
				this.totalExeTime = transaction.exeTime;
				
			} else {
				count++;
				this.totalWaitTime += transaction.waitTime;
				this.totalExeTime += transaction.exeTime;
			}
			
			this.totalTrans ++;
			
			long nowWindow = System.currentTimeMillis() / timeWindowSize;
			if(timeWindow < nowWindow){
				timeWindow = nowWindow;
				tps = 1;
			} else {
				tps++;
			}
			if(tps > maxTps){
				exceed = maxTps > 0;
				maxTps = tps;
				maxTpsOccurTime = new Date();
			}
			
			this.current = null;
		}
		
		return exceed;
	}
	
	/**
	 * 获取平均等待时间(毫秒)
	 * @return
	 */
	@Override
	public double getAvgWaitTime(){
		if(count > 0){
			return ((double)this.totalWaitTime) / count;
		}
		return 0D;
	}
	
	/**
	 * 获取平均等待时间(毫秒)
	 * @return
	 */
	@Override
	public double getAvgExeTime(){
		if(count > 0){
			return ((double)this.totalExeTime) / count;
		}
		return 0D;
	}
	
	@Override
	public void setTransWindowSize(int transWindowSize) {
		this.transWindowSize = transWindowSize;
	}

	@Override
	public int getTransWindowSize() {
		return this.transWindowSize;
	}

	@Override
	public String description() {
		return toString();
	}
	
	@Override
	public String getIdentify() {
		return this.identify;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public long getTotalTrans() {
		return this.totalTrans;
	}

	@Override
	public int getTps() {
		return this.tps;
	}

	@Override
	public int getMaxTps() {
		return this.maxTps;
	}

	@Override
	public long getTimeWindowSize() {
		return this.timeWindowSize;
	}

	@Override
	public void setTimeWindowSize(long timeWindowSize) {
		this.timeWindowSize = timeWindowSize;
	}
	
	
	
	@Override
	public String toString() {
		return "BufferMonitor [open=" + open + ", identify=" + identify
				+ ", transWindowSize=" + transWindowSize + ", count=" + count
				+ ", totalWaitTime=" + totalWaitTime + ", totalExeTime="
				+ totalExeTime + ", totalTrans=" + totalTrans + ", current="
				+ current + ", maxAvgWaitTime=" + maxAvgWaitTime
				+ ", maxAvgWaitOccurTime=" + format(maxAvgWaitOccurTime)
				+ ", maxAvgExeTime=" + maxAvgExeTime + ", maxAvgExeOccurTime="
				+ format(maxAvgExeOccurTime) + ", tps=" + tps + ", maxTps=" + maxTps
				+ ", maxTpsOccurTime=" + format(maxTpsOccurTime) + ", timeWindow="
				+ timeWindow + ", timeWindowSize=" + timeWindowSize + "]";
	}
	
	private String format(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}



	/**
	 * 具体的一个事务
	 */
	public static class Transaction {
		
		/**时间*/
		private long time = System.currentTimeMillis();
		
		/**事务等待处理的时间*/
		private long waitTime = 0;
		
		/**事务处理的时间*/
		private long exeTime = 0;
		
		/**
		 * 开始事务
		 */
		public void begin(){
			long currTime = System.currentTimeMillis();
			this.waitTime = currTime - time;
			this.time = currTime;
		}
		
		/**
		 * 结束事务
		 */
		public void end(){
			long currTime = System.currentTimeMillis();
			this.exeTime = currTime - time;
		}

		/**
		 * 获取事务等待处理的时间(毫秒)
		 * @return
		 */
		public long getWaitTime() {
			return waitTime;
		}

		/**
		 *  获取事务处理的时间(毫秒)
		 * @return
		 */
		public long getExeTime() {
			return exeTime;
		}
		
	}

	@Override
	public String getMaxAvgWaitOccurTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maxAvgWaitOccurTime);
	}

	@Override
	public String getMaxAvgExeOccurTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maxAvgExeOccurTime);
	}

	@Override
	public String getMaxTpsOccurTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maxTpsOccurTime);
	}

}
