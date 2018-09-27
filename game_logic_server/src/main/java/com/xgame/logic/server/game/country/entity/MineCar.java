package com.xgame.logic.server.game.country.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;

/**
 * 矿车
 * @author jacky.jiang
 *
 */
public class MineCar implements JBaseTransform {

	/**
	 * 矿车资源类型
	 */
	@Tag(1)
	private int uid;
	
	/**
	 * 采集资源类型
	 */
	@Tag(2)
	private int resourceType;
	
	/**
	 * 上一次领取奖励时间
	 */
	@Tag(3)
	private long lastRewardTime;

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public long getLastRewardTime() {
		return lastRewardTime;
	}

	public void setLastRewardTime(long lastRewardTime) {
		this.lastRewardTime = lastRewardTime;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("uid", uid);
		jBaseData.put("resourceType", resourceType);
		jBaseData.put("lastRewardTime", lastRewardTime);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.uid = jBaseData.getInt("uid", 0);
		this.resourceType = jBaseData.getInt("resourceType", 0);
		this.lastRewardTime = jBaseData.getLong("lastRewardTime", 0);
	}
	
}
