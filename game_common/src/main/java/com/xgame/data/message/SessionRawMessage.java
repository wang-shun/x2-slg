package com.xgame.data.message;

/**
 * 服务器接受到的消息
 * @author jacky.jiang
 *
 */
public class SessionRawMessage {

    private int msgID;
    private int callbackId;
    private long sessionID;
    private byte[] buffer;

    public SessionRawMessage(int msgID, long sessionID, int callbackId, byte[] buffer) {
        this.sessionID = sessionID;
        this.buffer = buffer;
        this.msgID = msgID;
        this.callbackId = callbackId;
    }

    public long getSessionID() {
        return sessionID;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public int getMsgID() {
        return msgID;
    }

	public int getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}
    
}
