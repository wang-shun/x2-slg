package com.xgame.logic.server.game.country.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;

import io.protostuff.Tag;

/**
 *
 *2016-12-13  15:20:59
 *@author ye.yuan
 *
 */
public class BlockBuild extends XBuild{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7016750183587256668L;
	
	@Tag(7)
	private int removeTime;

	public int getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(int removeTime) {
		this.removeTime = removeTime;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = super.toJBaseData();
		jBaseData.put("removeTime", removeTime);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		super.fromJBaseData(jBaseData);
		this.removeTime = jBaseData.getInt("removeTime", 0);
	}
	
}
