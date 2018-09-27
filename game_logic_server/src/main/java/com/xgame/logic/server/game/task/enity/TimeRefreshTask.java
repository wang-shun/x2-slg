package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 时间刷新任务
 * @author zehong.he
 *
 */
public class TimeRefreshTask  implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7199295763962438586L;

	
	/**刷新时间*/
	@Tag(1)
	private long refreshTime;
	/**每日任务*/
	@Tag(2)
	private List<TimeTask> dayTask = new ArrayList<TimeTask>();
	/**军团任务*/
	@Tag(3)
	private List<TimeTask> guildTask = new ArrayList<TimeTask>();
	
	public long getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}
	public List<TimeTask> getDayTask() {
		return dayTask;
	}
	public void setDayTask(List<TimeTask> dayTask) {
		this.dayTask = dayTask;
	}
	public List<TimeTask> getGuildTask() {
		return guildTask;
	}
	public void setGuildTask(List<TimeTask> guildTask) {
		this.guildTask = guildTask;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("refreshTime", refreshTime);
		
		List<JBaseData> dayTaskList = new ArrayList<JBaseData>();
		for (int i = 0; i < dayTask.size(); i++) {
			TimeTask timeTask = dayTask.get(i);
			dayTaskList.add(timeTask.toJBaseData());
		}
		jbaseData.put("dayTask", dayTaskList);
		
		List<JBaseData> guildTaskList = new ArrayList<JBaseData>();
		for (int i = 0; i < guildTask.size(); i++) {
			TimeTask timeTask = guildTask.get(i);
			guildTaskList.add(timeTask.toJBaseData());
		}
		jbaseData.put("guildTask", guildTaskList);
		
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.refreshTime = jBaseData.getLong("refreshTime", 0);
		List<JBaseData> dayTask = jBaseData.getSeqBaseData("dayTask");
		for(JBaseData jBaseData2 : dayTask) {
			TimeTask timeTask = new TimeTask();
			timeTask.fromJBaseData(jBaseData2);
			this.dayTask.add(timeTask);
		}
		
		List<JBaseData> guildTaskList = jBaseData.getSeqBaseData("guildTask");
		for(JBaseData jBaseData2 : guildTaskList) {
			TimeTask timeTask = new TimeTask();
			timeTask.fromJBaseData(jBaseData2);
			this.guildTask.add(timeTask);
		}
	}
}
