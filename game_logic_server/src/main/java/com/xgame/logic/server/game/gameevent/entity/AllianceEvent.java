package com.xgame.logic.server.game.gameevent.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 联盟事件信息
 * @author dingpeng.qu
 *
 */
public class AllianceEvent extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4706177409341410813L;
	/**
	 * id
	 */
	private Long allianceId;
	
	/**
	 * 联盟事件详细信息 
	 * key eventId
	 */
	private Map<Long,EventScoreInfo> eventScoreInfoMap = new ConcurrentHashMap<Long,EventScoreInfo>();

	@Override
	public Long getId() {
		return allianceId;
	}

	@Override
	public void setId(Long k) {
		this.allianceId = k;
	}
	
	public Long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}

	public Map<Long, EventScoreInfo> getEventScoreInfoMap() {
		return eventScoreInfoMap;
	}

	public void setEventScoreInfoMap(Map<Long, EventScoreInfo> eventScoreInfoMap) {
		this.eventScoreInfoMap = eventScoreInfoMap;
	}
	
	@SuppressWarnings("rawtypes")
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("allianceId", allianceId);
		
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
		this.allianceId = jBaseData.getLong("allianceId", 0);
		
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("eventScoreInfoMap");
		for(JBaseData jBaseData2  : jBaseDatas) {
			EventScoreInfo eventScoreInfo = new EventScoreInfo();
			eventScoreInfo.fromJBaseData(jBaseData2);
			long eventId = jBaseData2.getLong("eventId", 0);
			this.eventScoreInfoMap.put(eventId, eventScoreInfo);
		}
	}

}
