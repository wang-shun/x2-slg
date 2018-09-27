package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

/**
 *
 *2016-8-10  11:08:44
 *@author ye.yuan
 *
 */
public class TechTimerTaskData {

	@Tag(1)
	private int scienceId;
	
	@Tag(2)
	private int state;
	
	@Tag(3)
	private int uid;
	
	private int level;
	
	public TechTimerTaskData() {
		
	}
	
	
	public TechTimerTaskData(int sid, int state, int uid, int level) {
		super();
		this.scienceId = sid;
		this.state = state;
		this.uid = uid;
		this.level = level;
	}

	public int getSid() {
		return scienceId;
	}

	public void setSid(int sid) {
		this.scienceId = sid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
