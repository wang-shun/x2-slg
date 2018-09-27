package com.xgame.logic.server.game.timertask.entity.system.data;

import io.protostuff.Tag;


/**
 * 军团宝箱倒计时
 * @author dingpeng.qu
 *
 */
public class AllianceBoxTimerTaskData {

	@Tag(1)
	private long allianceId;
	
	@Tag(2)
	private long boxId;
	
	public AllianceBoxTimerTaskData() {
		
	}
	
	public AllianceBoxTimerTaskData(int allianceId, int boxId) {
		super();
		this.allianceId = allianceId;
		this.boxId = boxId;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}
	
}
