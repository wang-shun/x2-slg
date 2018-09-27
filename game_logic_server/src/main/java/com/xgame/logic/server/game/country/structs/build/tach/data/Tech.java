package com.xgame.logic.server.game.country.structs.build.tach.data;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 *
 *2016-7-25  10:51:36
 *@author ye.yuan
 *
 */
public class Tech implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Tag(1)
	private int id;
	@Tag(2)
	private int level;
	@Tag(3)
	private int state;
	@Tag(4)
	private Map<Integer, Integer> upLevelBefore = new HashMap<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Map<Integer, Integer> getUpLevelBefore() {
		return upLevelBefore;
	}
	public void setUpLevelBefore(Map<Integer, Integer> upLevelBefore) {
		this.upLevelBefore = upLevelBefore;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id",id);
		jbaseData.put("level",level);
		jbaseData.put("state",state);
		jbaseData.put("upLevelBefore", JsonUtil.toJSON(upLevelBefore));
		return jbaseData;
	}
	
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getInt("id",0);
		this.level = jBaseData.getInt("level",0);
		this.state = jBaseData.getInt("state",0);
		String upLevelBeforeStr = jBaseData.getString("upLevelBefore", "");
		if(!StringUtils.isEmpty(upLevelBeforeStr)) {
			Map<Integer, Integer> map = JsonUtil.fromJSON(upLevelBeforeStr, Map.class);
			this.upLevelBefore = map;
		}
	}
	
}
