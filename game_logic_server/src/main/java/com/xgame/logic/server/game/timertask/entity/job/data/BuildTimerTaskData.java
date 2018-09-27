package com.xgame.logic.server.game.timertask.entity.job.data;

import com.xgame.logic.server.game.country.bean.Vector2Bean;

import io.protostuff.Tag;

/**
 *
 *2016-8-09  11:27:40
 *@author ye.yuan
 *
 */
public class BuildTimerTaskData {

	@Tag(1)
	private int sid;
	
	@Tag(2)
	private int buildingUid;
	
	@Tag(3)
	private int state;
	
	private int level;
	
	private Vector2Bean vector2Bean;
	
	public BuildTimerTaskData() {
		
	}
	
	public BuildTimerTaskData(int sid, int buildingUid, int state, int level, Vector2Bean vector2Bean) {
		this.sid = sid;
		this.buildingUid = buildingUid;
		this.state = state;
		this.level = level;
		this.vector2Bean = vector2Bean;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Vector2Bean getVector2Bean() {
		return vector2Bean;
	}

	public void setVector2Bean(Vector2Bean vector2Bean) {
		this.vector2Bean = vector2Bean;
	}

	public int getBuildingUid() {
		return buildingUid;
	}

	public void setBuildingUid(int buildingUid) {
		this.buildingUid = buildingUid;
	}
	
}
