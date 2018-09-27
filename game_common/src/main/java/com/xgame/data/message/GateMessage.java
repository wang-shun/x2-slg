package com.xgame.data.message;

import java.io.Serializable;

/**
 * 发往网关的消息
 * @author jacky.jiang
 *
 */
public class GateMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5203673874949736918L;

	/**
	 * 协议id
	 */
	private int msgId;
	
	/**
	 * 发送时间
	 */
	private long sendTime;
	
	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	
}
