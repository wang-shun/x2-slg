package com.xgame.logic.server.core.utils.geometry.data;

import java.io.Serializable;

import io.protostuff.Tag;

/**
 *
 *2016-9-23  14:14:44
 *@author ye.yuan
 *
 */
public class InteractiveInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	public long activerUid;
	
	@Tag(2)
	public long taskId;

	public void enable(long activerUid, long taskId) {
		this.activerUid = activerUid;
		this.taskId = taskId;
	}
	
	public void  clear() {
		activerUid=0;
		taskId=0;
	}
	
	public boolean  isEnable(){
		return activerUid==0?false:true;
	}
	
}
