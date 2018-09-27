package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 联盟科技
 * @author jacky.jiang
 *
 */
public class AllianceScience implements JBaseTransform{
	
	/**
	 * 唯一id由联盟id#tid构成（allianceId#tid）
	 */
	private String id;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 建筑模版id
	 */
	private int tid;
	
	/**
	 * 建筑等级
	 */
	private int level;
	
	/**
	 * 当前经验
	 */
	private int exp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("tid", tid);
		jbaseData.put("level", level);
		jbaseData.put("exp", exp);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getString("id", "");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.tid = jBaseData.getInt("tid", 0);
		this.level = jBaseData.getInt("level", 0);
		this.exp = jBaseData.getInt("exp", 0);
	}
	
}
