package com.xgame.logic.server.game.task.enity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

import io.protostuff.Tag;

public class RewardsStatus implements Serializable, JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private int id;//宝箱ID
	@Tag(2)
	private int status; //0-未领取；1-已领取
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id",id);
		jbaseData.put("status",status);
		return jbaseData;
	}
	
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getInt("id",0);
		this.status = jBaseData.getInt("status",0);
	}
	
}
