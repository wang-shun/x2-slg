package com.xgame.data.message;


import java.util.List;

/**
 * Created by youxl
 */
public class ClientMsg extends GateMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5345748370568549753L;
	private List<Long> sessionIDList;
	private int callbackId;
	private int errorCode;
    private boolean immediate;
    private byte[] contents;

    /**
     * 普通消息
     * @param msgID
     * @param sessionIDList
     * @param immediate
     * @param contents
     */
    public ClientMsg(int msgID, List<Long> sessionIDList, boolean immediate, byte[] contents) {
        this.sessionIDList = sessionIDList;
        this.immediate = immediate;
        this.contents = contents;
        this.setMsgId(msgID);
    }
    
    /**
     * 带有返回值得消息
     * @param msgID
     * @param sessionIDList
     * @param immediate
     * @param callbackId
     * @param errorCode
     * @param contents
     */
    public ClientMsg(int msgID, List<Long> sessionIDList, boolean immediate, int callbackId, int errorCode, byte[] contents) {
        this.sessionIDList = sessionIDList;
        this.immediate = immediate;
        this.contents = contents;
        this.callbackId = callbackId;
        this.errorCode = errorCode;
        this.setMsgId(msgID);
    }
    
    public boolean isImmediate() {
        return immediate;
    }

    public byte[] getContents() {
        return contents;
    }

    public List<Long> getSessionIDList() {
        return sessionIDList;
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
}
