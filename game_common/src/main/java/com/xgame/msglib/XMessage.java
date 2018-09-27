package com.xgame.msglib;

import com.xgame.msglib.able.Communicationable;


/**
 *
 *2016-8-20  16:27:10
 *@author ye.yuan
 *
 */
public abstract class XMessage implements Communicationable{
	
	/**回调id*/
	public transient int callbackId;
	/**错误码*/
	public transient int errorCode;
	
	public int getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorEnum) {
		this.errorCode = errorEnum;
	}
	
}
