package com.xgame.data.message;


/**
 * 反悔客户端消息
 * @author jacky.jiang
 *
 */
public class ResponseMessage {

	// 消息id
    private int msgID;
    // 回掉id
    private int callbackId;
    //
    private int errorcode;
    // 字节数组
    private byte[] bytes;

    public ResponseMessage(int msgID, int callbackId, int errorCode, byte[] bytes) {
        this.msgID = msgID;
        this.bytes = bytes;
        this.callbackId = callbackId;
        this.errorcode = errorCode;
    }

    public int getMsgID() {
        return msgID;
    }

    public byte[] getBytes() {
        return bytes;
    }

	public int getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
    
	
}
