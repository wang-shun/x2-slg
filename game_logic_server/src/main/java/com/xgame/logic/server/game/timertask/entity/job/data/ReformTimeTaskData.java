package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

public class ReformTimeTaskData {

	@Tag(1)
	private int campType;
	@Tag(2)
	private long soldierId;
	@Tag(3)
	private int num;
	
	public ReformTimeTaskData() {
		
	}
	
	public ReformTimeTaskData(int campType, long soldierId, int num) {
		super();
		this.campType = campType;
		this.soldierId = soldierId;
		this.num = num;
	}
	public int getCampType() {
		return campType;
	}
	public void setCampType(int campType) {
		this.campType = campType;
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