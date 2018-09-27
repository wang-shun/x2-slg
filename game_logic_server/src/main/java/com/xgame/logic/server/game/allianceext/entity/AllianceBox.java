package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 联盟宝箱信息
 * @author jacky.jiang
 *
 */
public class AllianceBox implements JBaseTransform{
	
	/**
	 * id
	 */
	private long id;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 模版id
	 */
	private int boxTid;
	
	/**
	 * 开始时间
	 */
	private long startTime;
	
	/**
	 * 结束时间
	 */
	private long endTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public int getBoxTid() {
		return boxTid;
	}

	public void setBoxTid(int boxTid) {
		this.boxTid = boxTid;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("boxTid", boxTid);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("startTime", startTime);
		jbaseData.put("endTime", endTime);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.boxTid = jBaseData.getInt("boxTid", 0);
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.startTime = jBaseData.getLong("startTime", 0);
		this.endTime = jBaseData.getLong("endTime", 0);
	}
	
}
