package com.xgame.logic.server.game.gameevent.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 玩家事件信息
 * @author dingpeng.qu
 *
 */
public class PlayerEvent extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6588902721175196812L;
	/**
	 * id
	 */
	private Long playerId;
	/**
	 * 玩家事件详细信息
	 */
	private Map<Long,EventScoreInfo> eventScoreInfoMap = new ConcurrentHashMap<Long,EventScoreInfo>();

	@Override
	public Long getId() {
		return playerId;
	}

	@Override
	public void setId(Long k) {
		this.playerId = k;
	}
	
	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Map<Long, EventScoreInfo> getEventScoreInfoMap() {
		return eventScoreInfoMap;
	}

	public void setEventScoreInfoMap(Map<Long, EventScoreInfo> eventScoreInfoMap) {
		this.eventScoreInfoMap = eventScoreInfoMap;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		List<JBaseData> eventScoreInfoMapJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : eventScoreInfoMap.entrySet()) {
			JBaseData jBaseData2 = ((JBaseTransform) entry.getValue()).toJBaseData();
			jBaseData2.put("eventId", entry.getKey());
			eventScoreInfoMapJBaseList.add(jBaseData2);
		}
		
		jbaseData.put("eventScoreInfoMap", eventScoreInfoMapJBaseList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		List<JBaseData> eventScoreInfoJBaseData = jBaseData.getSeqBaseData("eventScoreInfoMap");
		for(JBaseData jBaseData2 : eventScoreInfoJBaseData) {
			EventScoreInfo eventScoreInfo = new EventScoreInfo();
			eventScoreInfo.fromJBaseData(jBaseData2);
			long eventId = jBaseData2.getLong("eventId", 0);
			eventScoreInfoMap.put(eventId, eventScoreInfo);
		}
	}
}
