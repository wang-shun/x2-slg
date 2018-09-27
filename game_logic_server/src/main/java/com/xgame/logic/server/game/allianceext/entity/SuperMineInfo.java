package com.xgame.logic.server.game.allianceext.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 超级矿信息
 * @author dingpeng.qu
 *
 */
public class SuperMineInfo implements JBaseTransform{
	
	/**
	 * 剩余资源
	 */
	private long resourceLeft;
	
	/**
	 * 采集队列
	 * key playerId
	 * value marchId
	 */
	private Map<Long,Long> marchs = new ConcurrentHashMap<>();
	
	public long getResourceLeft() {
		return resourceLeft;
	}

	public void setResourceLeft(long resourceLeft) {
		this.resourceLeft = resourceLeft;
	}

	public Map<Long, Long> getMarchs() {
		return marchs;
	}

	public void setMarchs(Map<Long, Long> marchs) {
		this.marchs = marchs;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("resourceLeft", resourceLeft);
		
		List<JBaseData> marchsJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : marchs.entrySet()) {
			JBaseData jBaseData2 = new JBaseData();
			jBaseData2.put("playerId", entry.getKey());
			jBaseData2.put("marchId", entry.getValue());
			marchsJBaseList.add(jBaseData2);
		}
		jbaseData.put("marchs", marchsJBaseList);
		return jbaseData;
	}
	
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.resourceLeft = jBaseData.getLong("resourceLeft",0);
		
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("marchs");
		for(JBaseData jBaseData2  : jBaseDatas) {
			long playerId = jBaseData2.getLong("playerId", 0);
			long marchId = jBaseData2.getLong("marchId", 0);
			this.marchs.put(playerId, marchId);
		}
	}

}
