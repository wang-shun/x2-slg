package com.xgame.logic.server.game.commander.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *已学天赋
 *2016-10-09  11:20:10
 *@author ye.yuan
 *
 */
public class TalentData implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 天赋配置id
	 */
	@Tag(1)
	private int sid;
	
	/**
	 * 当前已点
	 */
	@Tag(2)
	private volatile int level;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("sid", sid);
		jBaseData.put("level", level);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.sid = jBaseData.getInt("sid", 0);
		this.level = jBaseData.getInt("level", 0);
	}
	
}
