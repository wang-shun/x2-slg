package com.xgame.logic.server.game.country.structs.build.Radar.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.game.country.structs.build.Radar.data.action.PlayerRadarBeAttacker;

import io.protostuff.Tag;

/**
 *雷达数据
 *2016-12-21  12:03:38
 *@author ye.yuan
 *
 */
public final class RadaData implements Serializable{

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

	
}
