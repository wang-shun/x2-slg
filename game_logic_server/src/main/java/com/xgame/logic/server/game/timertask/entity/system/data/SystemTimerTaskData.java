package com.xgame.logic.server.game.timertask.entity.system.data;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.ClassNameUtils;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.utils.TimeUtils;


/**
 * 系统倒计时
 * @author jacky.jiang
 *
 */
public class SystemTimerTaskData extends AbstractEntity<Long> implements Serializable, JBaseTransform, Delayed  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4988302976032539378L;

	// 任务id
	@Tag(1)
	private long taskId;

	// queueid
	@Tag(2)
	private int queueId;
	
	// 过程所需时间
	@Tag(3)
	private int taskTime;// 秒

	// 结束时间(定时器到期的执行时间)
	@Tag(4)
	private int triggerTime; // 秒
	
	// 开始时间
	@Tag(5)
	private int startTime;
	
	// 队列创建时间
	@Tag(6)
	private long createTime;
	
	// 参数
	@Tag(7)
	private Object param;
	
	@Override
	public Long getId() {
		return taskId;
	}

	@Override
	public void setId(Long k) {
		this.taskId = k;
	}
	
	/**
	 * 结束
	 * @return
	 */
	public boolean isOver() {
		if(triggerTime <= TimeUtils.getCurrentTime()) {
			return true;
		}
		return false;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
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

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("taskId", taskId);
		jbaseData.put("queueId", queueId);
		jbaseData.put("taskTime", taskTime);
		jbaseData.put("triggerTime", triggerTime);
		jbaseData.put("startTime", startTime);
		jbaseData.put("createTime", createTime);
		if(param != null) {
			jbaseData.put("param", JsonUtil.toJSON(param));
			jbaseData.put("clazz", param.getClass().getName());
		}
		return jbaseData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fromJBaseData(JBaseData jBaseData) {
		this.taskId = jBaseData.getLong("taskId", 0);
		this.queueId = jBaseData.getInt("queueId", 0);
		this.taskTime = jBaseData.getInt("taskTime", 0);
		this.triggerTime = jBaseData.getInt("triggerTime", 0);
		this.startTime = jBaseData.getInt("startTime", 0);
		this.createTime = jBaseData.getLong("createTime", 0);
		
		String paramStr = jBaseData.getString("param", "");
		if(!StringUtils.isEmpty(paramStr)) {
			String clazzStr = jBaseData.getString("clazz", "");
			Class clazz = ClassNameUtils.getClass(clazzStr);
			this.param = JsonUtil.fromJSON(paramStr, clazz);
		}
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
		SystemTimerTaskData other = (SystemTimerTaskData) obj;
		if (this.taskId != other.getId())
			return false;
		return true;
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
}
