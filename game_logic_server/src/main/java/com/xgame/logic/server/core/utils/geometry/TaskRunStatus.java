package com.xgame.logic.server.core.utils.geometry;


/**
 *
 *2016-9-22  15:17:19
 *@author ye.yuan
 *
 */
public enum TaskRunStatus {

	NORMAL((byte)0),
	RESET((byte)1),
	SUB((byte)2),
	REMOVE((byte)3),
	;
	
	private byte status;
	
	private TaskRunStatus(byte status) {
		this.status=status;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
	
	public static TaskRunStatus valueOf(byte status) {
		if(status == NORMAL.status) {
			return NORMAL;
		} else if(status == RESET.status) {
			return RESET;
		} else if(status == SUB.status) {
			return SUB;
		} else if(status == REMOVE.status) {
			return REMOVE;
		} else {
			return null;
		}
	}

	
}
