package com.xgame.logic.server.game.timertask.entity.system.data;

/**
 * 联盟建筑
 * @author dingpeng.qu
 *
 */
public class AllianceBuildTimerTaskData {
	/**
	 * 联盟建筑唯一ID
	 */
	private long allianceBuildId;
	
	public AllianceBuildTimerTaskData(){
		
	}

	public AllianceBuildTimerTaskData(long allianceBuildId) {
		super();
		this.allianceBuildId = allianceBuildId;
	}

	public long getAllianceBuildId() {
		return allianceBuildId;
	}

	public void setAllianceBuildId(long allianceBuildId) {
		this.allianceBuildId = allianceBuildId;
	}
}
