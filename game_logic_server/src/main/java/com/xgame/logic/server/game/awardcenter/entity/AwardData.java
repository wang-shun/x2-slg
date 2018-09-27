package com.xgame.logic.server.game.awardcenter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

public class AwardData implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7269002279563327061L;
	
	/**
	 * 奖励treeMap key为排序index 根据现有key值递增
	 */
	private TreeMap<Integer, AwardDB> awardDBs = new TreeMap<>();

	public TreeMap<Integer, AwardDB> getAwardDBs() {
		return awardDBs;
	}

	public void setAwardDBs(TreeMap<Integer, AwardDB> awardDBs) {
		this.awardDBs = awardDBs;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		List<JBaseData> awardDBsJBaseList = new ArrayList<JBaseData>();
		for (Map.Entry entry : awardDBs.entrySet()) {
			awardDBsJBaseList.add(((JBaseTransform) entry.getValue()).toJBaseData());
		}
		jbaseData.put("awardDBs", awardDBsJBaseList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("awardDBs");
		for(JBaseData jBaseData2  : jBaseDatas) {
			AwardDB awardDB = new AwardDB();
			awardDB.fromJBaseData(jBaseData2);
			this.awardDBs.put(awardDB.getIndex(), awardDB);
		}
	}

}
