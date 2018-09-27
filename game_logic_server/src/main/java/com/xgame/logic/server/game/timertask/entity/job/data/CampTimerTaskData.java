package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

/**
 * 军营生产
 *2016-8-15  11:47:29
 *@author ye.yuan
 *
 */
public class CampTimerTaskData {
	
	// 兵营id
	@Tag(1)
	private int campId;
	
	// 建筑唯一id
	@Tag(2)
	private int buildUid;
	
	//士兵id
	@Tag(3)
	private long soldierId;
	
	// 士兵数量
	@Tag(4)
	private int num;
	
	public CampTimerTaskData() {
		
	}

	public CampTimerTaskData(int campId, int buildUid, long soldierId, int num) {
		super();
		this.campId = campId;
		this.buildUid = buildUid;
		this.soldierId = soldierId;
		this.num = num;
	}

	public int getCampId() {
		return campId;
	}

	public void setCampId(int campId) {
		this.campId = campId;
	}

	public int getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(int buildUid) {
		this.buildUid = buildUid;
	}
	
	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long soldierId) {
		this.soldierId = soldierId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
