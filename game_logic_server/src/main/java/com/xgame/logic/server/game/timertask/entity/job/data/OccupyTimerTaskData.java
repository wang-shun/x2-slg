package com.xgame.logic.server.game.timertask.entity.job.data;

import io.protostuff.Tag;


/**
 * 占领时间信息
 * @author jacky.jiang
 *
 */
public class OccupyTimerTaskData {
	
	/**
	 * 占领出征队伍id
	 */
	@Tag(1)
	public long marchId;
	
	public OccupyTimerTaskData() {
		
	}

	public OccupyTimerTaskData(long marchId) {
		super();
		this.marchId = marchId;
	}
	
}
