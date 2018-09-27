package com.xgame.data.message;

/**
 * 发往服务器的消息
 * @author jacky.jiang
 *
 */
public class SessionMessage extends ClientMessage {

    private int msgID;
    private int calbackId;
    private byte[] bytes;

    public SessionMessage(long sessionID, int msgID, int callbackId, byte[] bytes) {
        this.bytes = bytes;
        this.msgID = msgID;
        this.calbackId = callbackId;
        setSessionID(sessionID);
    }

    public int getMsgID() {
        return msgID;
    }

    public byte[] getBytes() {
        return bytes;
    }

	public int getCalbackId() {
		return calbackId;
	}

	public void setCalbackId(int calbackId) {
		this.calbackId = calbackId;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
