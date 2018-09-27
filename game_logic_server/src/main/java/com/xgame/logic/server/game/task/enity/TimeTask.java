package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 时间任务（日常、军团任务）
 * @author zehong.he
 *
 */
public class TimeTask implements Serializable, JBaseTransform{
	
	public static final int STATE_NO_OPEN = 0;//0未开始
	public static final int STATE_RUNNING = 1;//1进行中
	public static final int STATE_COMPLETE = 2;//2已完成
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1904886903199493781L;
	
	@Tag(1)
	private long id;			//唯一ID

	@Tag(2)
	private int taskId;			//模板ID
	
	@Tag(3)
	private int state;	  		//任务状态（0未开始1进行中2已完成）
	
	@Tag(4)
	private int startTime; 	//开始时间
	
	@Tag(5)
	private int endTime; 		//结束时间
	
	
	

	public static TimeTask valueOf(int taskId) {
		TimeTask baseTask = new TimeTask();
		baseTask.setTaskId(taskId);
		return baseTask;
	}


	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("taskId", taskId);
		jbaseData.put("state", state);
		jbaseData.put("startTime", startTime);
		jbaseData.put("endTime", endTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.taskId = jBaseData.getInt("taskId", 0);
		this.state = jBaseData.getInt("state", 0);
		this.startTime = jBaseData.getInt("startTime", 0);
		this.endTime = jBaseData.getInt("endTime", 0);
	}


	public static int getStateNoOpen() {
		return STATE_NO_OPEN;
	}


	public static int getStateRunning() {
		return STATE_RUNNING;
	}


	public static int getStateComplete() {
		return STATE_COMPLETE;
	}


	public long getId() {
		return id;
	}


	public int getTaskId() {
		return taskId;
	}


	public int getState() {
		return state;
	}


	public int getStartTime() {
		return startTime;
	}


	public int getEndTime() {
		return endTime;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


	public void setState(int state) {
		this.state = state;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}


	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
