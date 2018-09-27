package com.xgame.logic.server.game.gameevent.entity;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 上次事件玩家最大积分值
 * @author dingpeng.qu
 *
 */
public class EventMaxScore extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4706177409341410813L;
	
	private Long eventId;
	
	private int maxScore;

	@Override
	public Long getId() {
		return eventId;
	}

	@Override
	public void setId(Long k) {
		this.eventId = k;
	}
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("eventId", eventId);
		jbaseData.put("maxScore", maxScore);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.eventId = jBaseData.getLong("eventId", 0);
		this.maxScore = jBaseData.getInt("maxScore", 0);
	}

}
