package com.xgame.logic.server.game.world.entity.territory;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;


/**
 * 联盟领地
 * @author jacky.jiang
 *
 */
public class AllianceTerritory implements JBaseTransform {
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 *  领地信息
	 */
	private ConcurrentHashSet<Integer> territory = new ConcurrentHashSet<Integer>();
	
	public AllianceTerritory(long allianceId) {
		this.allianceId = allianceId;
	}
	
	public AllianceTerritory() {

	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public ConcurrentHashSet<Integer> getTerritory() {
		return territory;
	}

	public void setTerritory(ConcurrentHashSet<Integer> territory) {
		this.territory = territory;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("territory", JsonUtil.toJSON(territory));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.allianceId = jBaseData.getLong("allianceId", 0);
		String territoryStr = jBaseData.getString("territory", "");
		if(!StringUtils.isEmpty(territoryStr)) {
			this.territory = JsonUtil.fromJSON(territoryStr, ConcurrentHashSet.class);
		}
	}
	
}
