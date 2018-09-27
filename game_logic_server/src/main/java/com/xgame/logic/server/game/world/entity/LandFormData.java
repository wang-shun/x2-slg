package com.xgame.logic.server.game.world.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 地表信息
 * @author jacky.jiang
 *
 */
public class LandFormData implements JBaseTransform {
	
	private int surfaceId;
	private int groundId;
	
	public int getGroundId() {
		return groundId;
	}
	
	public void setGroundId(int groundId) {
		this.groundId = groundId;
	}

	public int getSurfaceId() {
		return surfaceId;
	}

	public void setSurfaceId(int surfaceId) {
		this.surfaceId = surfaceId;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("surfaceId", surfaceId);
		jbaseData.put("groundId", groundId);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.surfaceId = jBaseData.getInt("surfaceId", 0);
		this.groundId = jBaseData.getInt("groundId", 0);
	}

}
