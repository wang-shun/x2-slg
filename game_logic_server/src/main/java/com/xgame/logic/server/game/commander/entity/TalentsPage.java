package com.xgame.logic.server.game.commander.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *天赋页
 *2016-10-10  12:23:25
 *@author ye.yuan
 *
 */
public class TalentsPage implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3236435597709733913L;
	
	/**
	 * 天赋页ID
	 */
	@Tag(1)
	private int id;
	
	/**
	 * 本页已用天赋缓存  便于发送数据的时候快速获取
	 */
	@Tag(2)
	private volatile int useTalentPoints;
	
	/**
	 * 已学天赋
	 */
	@Tag(3)
	private Map<Integer, TalentData> talents = new ConcurrentHashMap<>();
	
	public TalentsPage(){
		
	}
	
	public TalentsPage(int id){
		this.id = id;
	}

	public int getUseTalentPoints() {
		return useTalentPoints;
	}

	public void setUseTalentPoints(int useTalentPoints) {
		this.useTalentPoints = useTalentPoints;
	}

	public Map<Integer, TalentData> getTalents() {
		return talents;
	}

	public void setTalents(Map<Integer, TalentData> talents) {
		this.talents = talents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("useTalentPoints", useTalentPoints);
		jBaseData.put("id", id);
		
		List<JBaseData> talentJBaseData = new ArrayList<JBaseData>();
		for(TalentData talentData : this.talents.values()) {
			talentJBaseData.add(talentData.toJBaseData());
		}
		jBaseData.put("talents", talentJBaseData);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.useTalentPoints = jBaseData.getInt("useTalentPoints", 0);
		this.id = jBaseData.getInt("id", 0);
		
		List<JBaseData> talentJBaseData = jBaseData.getSeqBaseData("talents");
		for(JBaseData jBaseData2 : talentJBaseData) {
			TalentData talentData = new TalentData();
			talentData.fromJBaseData(jBaseData2);
			this.talents.put(talentData.getSid(), talentData);
		}
	}
	
}
