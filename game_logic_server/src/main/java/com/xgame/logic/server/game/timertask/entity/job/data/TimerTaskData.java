package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.ClassNameUtils;
import com.xgame.logic.server.core.utils.JsonUtil;


/**
 * 时间队列数据
 * @author jacky.jiang
 *
 */
public class TimerTaskData extends AbstractEntity<Long> implements Serializable, JBaseTransform, Delayed{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4626271591333933621L;

	@Tag(1)
	private long taskId;

	@Tag(2)
	private long roleId;

	@Tag(3)
	private int eType;
	
	@Tag(4)
	private int queueId;
	
	// 过程所需时间
	@Tag(5)
	private int taskTime;// 秒

	// 结束时间(定时器到期的执行时间)
	@Tag(6)
	private int triggerTime; // 秒
	
	// 开始时间
	@Tag(7)
	private int startTime;
	
	// 队列当前执行次数
	@Tag(8)
	private int count;
	
	// 队列最大次数
	@Tag(9)
	private int maxCount;
	
	// 队列创建时间
	@Tag(10)
	private long createTime;
	
	/**
	 * 
	 */
	@Tag(11)
	private Object param;
	
	private transient ScheduledFuture<?> scheduledFuture;
	
	private String helpId;
	
	/**
	 * 事件队列是否执行完成
	 * @return
	 */
	public boolean isOver() {
		if(count >= maxCount) {
			return true;
		}
		return false;
	}
	
	/**
	 * 增加次数
	 */
	public void incrCount() {
		this.count = this.count + 1;
	}
	
	public void reset(int startTime,  int taskTime) {
		this.startTime = startTime;
		this.taskTime = taskTime;
		this.triggerTime = startTime + taskTime;
	}
	
	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	@Override
	public String toString() {
		return "TimerTaskData [taskId=" + taskId + ", roleId=" + roleId
				+ ", eType=" + eType + ", queueId=" + queueId + ", taskTime="
				+ taskTime + ", triggerTime=" + triggerTime + ", startTime="
				+ startTime + ", count=" + count + ", maxCount=" + maxCount
				+ ", createTime=" + createTime + "]";
	}

	@Override
	public Long getId() {
		return this.taskId;
	}

	@Override
	public void setId(Long k) {
		this.taskId = k;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int geteType() {
		return eType;
	}

	public void seteType(int eType) {
		this.eType = eType;
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public int getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(int taskTime) {
		this.taskTime = taskTime;
	}

	public int getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(int triggerTime) {
		this.triggerTime = triggerTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	public String getHelpId() {
		return helpId;
	}

	public void setHelpId(String helpId) {
		this.helpId = helpId;
	}
	
	@Override
	public int compareTo(Delayed o) {
		if (this == o) {
			return 0;
		}

		long diff = 0;
		if (o instanceof TimerTaskData) {
			TimerTaskData other = (TimerTaskData) o;
			diff = ((long)this.triggerTime) * 1000 - ((long)other.getTriggerTime()) * 1000;
		} else {
			diff = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
		}
		return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long diff = (((long)this.getTriggerTime()) * 1000 - System.currentTimeMillis());
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getTaskId().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimerTaskData other = (TimerTaskData) obj;
		if (this.taskId != other.getId())
			return false;
		return true;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("taskId", taskId);
		jbaseData.put("roleId", roleId);
		jbaseData.put("eType", eType);
		jbaseData.put("queueId", queueId);
		jbaseData.put("taskTime", taskTime);
		jbaseData.put("triggerTime", triggerTime);
		jbaseData.put("startTime", startTime);
		jbaseData.put("count", count);
		jbaseData.put("maxCount", maxCount);
		jbaseData.put("createTime", createTime);
		jbaseData.put("helpId", helpId);
		if(param != null) {
			jbaseData.put("param", JsonUtil.toJSON(param));
			jbaseData.put("clazz", param.getClass().getName());
		}
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.taskId = jBaseData.getLong("taskId", 0);
		this.roleId = jBaseData.getLong("roleId", 0);
		this.eType = jBaseData.getInt("eType", 0);
		this.queueId = jBaseData.getInt("queueId", 0);
		this.taskTime = jBaseData.getInt("taskTime", 0);
		this.triggerTime = jBaseData.getInt("triggerTime", 0);
		this.startTime = jBaseData.getInt("startTime", 0);
		this.count = jBaseData.getInt("count", 0);
		this.maxCount = jBaseData.getInt("maxCount", 0);
		this.createTime = jBaseData.getLong("createTime", 0);
		this.helpId = jBaseData.getString("helpId", "");
		
		String paramStr = jBaseData.getString("param", "");
		if(!StringUtils.isEmpty(paramStr)) {
			String clazzStr = jBaseData.getString("clazz", "");
			Class<?> clazz = ClassNameUtils.getClass(clazzStr);
			this.param = JsonUtil.fromJSON(paramStr, clazz);
		}
	}

}
