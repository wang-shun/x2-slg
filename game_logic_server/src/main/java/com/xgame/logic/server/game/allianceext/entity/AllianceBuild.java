package com.xgame.logic.server.game.allianceext.entity;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;


/**
 * 联盟建筑
 * @author jacky.jiang
 *
 */
public class AllianceBuild implements JBaseTransform {

	/**
	 * 
	 * 建筑唯一id(联盟id#建筑buildTid)
	 */
	private String id;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 建筑Tid
	 */
	private int buildTid;
	
	/**
	 * 建筑等级
	 */
	private int level;
	
	/**
	 * 世界坐标位置
	 */
	private Vector2Bean position;
	
	private long createTime;

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public int getBuildTid() {
		return buildTid;
	}

	public void setBuildTid(int buildTid) {
		this.buildTid = buildTid;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("buildTid", buildTid);
		jbaseData.put("level", level);
		jbaseData.put("position", JsonUtil.toJSON(position));
		jbaseData.put("createTime", createTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getString("id", "");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.buildTid = jBaseData.getInt("buildTid", 0);
		this.level = jBaseData.getInt("level", 0);
		
		String positionStr = jBaseData.getString("position", "");
		if(!StringUtils.isEmpty(positionStr)) {
			this.position = JsonUtil.fromJSON(positionStr, Vector2Bean.class);
		}
		
		this.createTime = jBaseData.getInt("createTime", 0);
	}
	
}
