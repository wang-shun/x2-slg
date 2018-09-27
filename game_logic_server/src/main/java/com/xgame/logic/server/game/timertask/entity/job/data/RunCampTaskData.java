package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;


/**
 * 行军队列信息
 * 2016-9-03  17:08:25
 *@author ye.yuan
 *
 */
public class RunCampTaskData {

	/**
	 * 世界行军id
	 */
	@Tag(1)
	private long worldMarchId;
	
	public RunCampTaskData(){
		
	}
	
	public RunCampTaskData(long worldMarchId) {
		super();
		this.worldMarchId = worldMarchId;
	}

	public long getWorldMarchId() {
		return worldMarchId;
	}

	public void setWorldMarchId(long worldMarchId) {
		this.worldMarchId = worldMarchId;
	}
	
}
