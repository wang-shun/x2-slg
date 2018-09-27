package com.xgame.logic.server.core.utils.geometry.data;

import java.io.Serializable;

import io.protostuff.Tag;

/**
 *
 *2016-9-23  14:32:51
 *@author ye.yuan
 *
 */
public class SodierTeam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * uid - 玩家id
	 */
	@Tag(1)
	public long targetSpriteUid;
	
	/**
	 * taskId - 任务id
	 */
	@Tag(2)
	public long teamId;

	public SodierTeam(long targetSpriteUid, long teamId) {
		super();
		this.targetSpriteUid = targetSpriteUid;
		this.teamId = teamId;
	}
	
}
