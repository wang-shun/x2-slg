package com.xgame.data.message;

import java.util.List;

/**
 * 服务器消息
 * @author jacky.jiang
 *
 */
public class ServerMessage {
    private int msgID;
    private List<Long> sessionIDList;
    private boolean immediate;
    private int callbackId;
    private int errorCode;
    private byte[] bytes;

    public ServerMessage(int msgID, List<Long> sessionIDList, boolean immediate, int callbackId, int errorCode, byte[] bytes) {
        this.sessionIDList = sessionIDList;
        this.immediate = immediate;
        this.bytes = bytes;
        this.errorCode = errorCode;
        this.callbackId = callbackId;
        this.msgID = msgID;
    }

    public List<Long> getSessionIDList() {
        return sessionIDList;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getMsgID() {
        return msgID;
    }

    public boolean isImmediate() {
        return immediate;
    }

	public int getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setSessionIDList(List<Long> sessionIDList) {
		this.sessionIDList = sessionIDList;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
