/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import javax.management.MXBean;

/**
 * 监控的MBean
 * @author jacky
 *
 */
@MXBean
public interface BufferMonitorMXBean {
	
	/**
	 * 打开监控
	 */
	void setOpen(boolean open);
	
	/**
	 * 查询是否开启
	 */
	boolean isOpen();
	
	/**
	 * 获取buffer的标识
	 * @return
	 */
	String getIdentify();

	/**
	 * 设置事务窗口大小
	 * @param window
	 */
	void setTransWindowSize(int transWindowSize);
	
	/**
	 * 获取事务窗口大小
	 * @return
	 */
	int getTransWindowSize();
	
	
	/**
	 * 时间窗口大小(毫秒)
	 * @return
	 */
	long getTimeWindowSize();
	
	/**
	 * 设置时间窗口大小(毫秒)
	 */
	void setTimeWindowSize(long timeWindowSize);
	
	/**
	 * 获取平均等待毫秒数
	 * @return
	 */
	double getAvgWaitTime();
	
	/**
	 * 历史最大的平均等待发生时间
	 * @return
	 */
	String getMaxAvgWaitOccurTime();
	
	/**
	 * 获取平均处理(入库)毫秒数
	 * @return
	 */
	double getAvgExeTime();
	
	/**
	 * 历史最大的平均执行发生时间
	 * @return
	 */
	String getMaxAvgExeOccurTime();
	
	/**
	 * 累计处理的事务
	 * @return
	 */
	long getTotalTrans();
	
	/**
	 * 当前时间窗口内的处理的事务数
	 * @return
	 */
	int getTps();
	
	/**
	 * 历史最大的时间窗口内事务数
	 * @return
	 */
	int getMaxTps();
	
	/**
	 * 历史最大的时间窗口内事务数发生时间
	 * @return
	 */
	String getMaxTpsOccurTime();
	
	/**
	 * 返回描述信息
	 * @return
	 */
	String description();
	
}
