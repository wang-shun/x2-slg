package com.xgame.logic.server.game.radar.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 *雷达数据
 *2016-12-21  12:03:38
 *@author ye.yuan
 *
 */
public class RadaData implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 攻击者列表    
	 */
	@Tag(1)
	private List<PlayerRadarBeAttacker> attackers = new ArrayList<>();
	
	@Tag(2)
	private Map<Integer,Map<Integer, InvestigateData>> investigates = new HashMap<Integer, Map<Integer,InvestigateData>>();
	
	/**
	 * 免侦查时间
	 */
	@Tag(3)
	private long avoidInvestTime;
	
	/**
	 * 侦查伪装时间
	 */
	@Tag(4)
	private long investPretendTime;
	

	public List<PlayerRadarBeAttacker> getAttackers() {
		return attackers;
	}

	public void setAttackers(List<PlayerRadarBeAttacker> attackers) {
		this.attackers = attackers;
	}
	
	public InvestigateData getInvestigateData(int x, int y) {
		Map<Integer, InvestigateData> map = investigates.get(x);
		if(map != null) {
			return map.get(y);
		}
		return null;
	}
	
	public void addInvestigateData(int x, int y, InvestigateData investigateData) {
		Map<Integer, InvestigateData> map = investigates.get(x);
		if(map == null) {
			map = new HashMap<Integer, InvestigateData>();
			investigates.put(x, map);
		}
		map.put(y, investigateData);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("attackers", JsonUtil.toJSON(attackers));
		jBaseData.put("investigates", JsonUtil.toJSON(investigates));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		String attackersString = jBaseData.getString("attackers", "");
		this.attackers  = JsonUtil.parseArray(attackersString, PlayerRadarBeAttacker.class);
		String investigatesString = jBaseData.getString("investigates", "");
		this.investigates = JsonUtil.fromJSON(investigatesString, Map.class);
	}

	public long getAvoidInvestTime() {
		return avoidInvestTime;
	}

	public void setAvoidInvestTime(long avoidInvestTime) {
		this.avoidInvestTime = avoidInvestTime;
	}

	public long getInvestPretendTime() {
		return investPretendTime;
	}

	public void setInvestPretendTime(long investPretendTime) {
		this.investPretendTime = investPretendTime;
	}
}
