package com.xgame.logic.server.game.gameevent.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 事件排行榜
 * @author dingpeng.qu
 *
 */
public class EventRank{
	
	/**
	 * id
	 */
	private Long id;
	/**
	 * id
	 */
	private Integer eventId;
	/**
	 * 排行榜数据
	 */
	private Map<String,Integer> map = new LinkedHashMap<String,Integer>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

}
