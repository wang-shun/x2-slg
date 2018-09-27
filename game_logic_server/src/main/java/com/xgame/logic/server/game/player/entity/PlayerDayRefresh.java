package com.xgame.logic.server.game.player.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.utils.TimeUtils;


/**
 * 玩家次数刷新
 * @author jacky.jiang
 *
 */
public class PlayerDayRefresh implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9034687212489287284L;

	/**
	 * 搜索挑战次数
	 */
	private int searchAttackNum;
	
	/**
	 * 刷新时间
	 */
	private long updateTime;

	public int getSearchAttackNum() {
		return searchAttackNum;
	}

	public void setSearchAttackNum(int searchAttackNum) {
		this.searchAttackNum = searchAttackNum;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 刷新
	 */
	public synchronized void refresh() {
		if(!TimeUtils.isToday(TimeUtils.getCurrentTimeMillis())) {
			searchAttackNum = 0;
		}
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("searchAttackNum", searchAttackNum);
		jbaseData.put("updateTime", updateTime);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.searchAttackNum = jBaseData.getInt("searchAttackNum", 0);
		this.updateTime = jBaseData.getLong("updateTime", 0);
	}

}

