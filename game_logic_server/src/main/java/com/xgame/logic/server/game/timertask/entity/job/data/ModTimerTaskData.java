package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;

import java.util.List;

import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;

/**
 *
 *2016-8-15  11:47:29
 *@author ye.yuan
 *
 */
public class ModTimerTaskData {

	@Tag(1)
	private int  buildUid;
	
	@Tag(2)
	private List<SoldierBrief>  soldierList;
	
	
	public ModTimerTaskData() {
		
	}
	
	public ModTimerTaskData(int buildUid, List<SoldierBrief> soldierList) {
		super();
		this.buildUid = buildUid;
		this.soldierList = soldierList;
	}

	public int getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(int buildUid) {
		this.buildUid = buildUid;
	}

	public List<SoldierBrief> getSoldierList() {
		return soldierList;
	}

	public void setSoldierList(List<SoldierBrief> soldierList) {
		this.soldierList = soldierList;
	}
	
}
