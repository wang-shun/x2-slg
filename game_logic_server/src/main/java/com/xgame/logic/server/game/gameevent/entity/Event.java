package com.xgame.logic.server.game.gameevent.entity;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 事件
 * @author dingpeng.qu
 *
 */
public class Event extends AbstractEntity<Long> implements JBaseTransform{
	
	private static final long serialVersionUID = 4065835933918599821L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 事件配置表Id
	 */
	private int eventId;
	/**
	 * 事件名称
	 */
	private int name;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 事件类型
	 */
	private int eventType;
	/**
	 * 开始时间
	 */
	private long startTime;
	/**
	 * 结束时间
	 */
	private long endTime;
	
	

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long k) {
		this.id = k;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("eventId", eventId);
		jbaseData.put("name", name);
		jbaseData.put("type", type);
		jbaseData.put("eventType", eventType);
		jbaseData.put("startTime", startTime);
		jbaseData.put("endTime", endTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.eventId = jBaseData.getInt("eventId", 0);
		this.name = jBaseData.getInt("name", 0);
		this.type = jBaseData.getInt("type", 0);
		this.eventType = jBaseData.getInt("eventType", 0);
		this.startTime = jBaseData.getLong("startTime", 0);
		this.endTime = jBaseData.getLong("endTime", 0);
	}
}
