package com.xgame.logic.server.game.country.structs.build.camp.data;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *
 *2016-11-22  14:27:33
 *@author ye.yuan
 *
 */
public class CampData implements JBaseTransform {

	
	@Tag(2)
	private Map<Long, SoldierBrief> soldierGives = new ConcurrentHashMap<>();

	public Map<Long, SoldierBrief> getSoldierGives() {
		return soldierGives;
	}

	public void setSoldierGives(Map<Long, SoldierBrief> soldierGives) {
		this.soldierGives = soldierGives;
	}
	
	public void addSoldier(long soldierId, int num) {
		SoldierBrief soldierBrief = soldierGives.get(soldierId);
		if(soldierBrief == null) {
			soldierBrief = new SoldierBrief();
			soldierBrief.setSoldierId(soldierId);
			soldierBrief.setNum(num);
			soldierGives.put(soldierId, soldierBrief);
		} else {
			soldierBrief.setNum(soldierBrief.getNum() + num);
		}
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		List<JBaseData> jBaseDatas = new ArrayList<JBaseData>();
		for(SoldierBrief soldierBrief : soldierGives.values()) {
			jBaseDatas.add(soldierBrief.toJBaseData());
		}
		jBaseData.put("soldierGives", jBaseDatas);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("soldierGives");
		for(JBaseData jBaseData2 : jBaseDatas) {
			SoldierBrief soldierBrief = new SoldierBrief();
			soldierBrief.fromJBaseData(jBaseData2);
			soldierGives.put(soldierBrief.getSoldierId(),soldierBrief);
		}
	}
}
