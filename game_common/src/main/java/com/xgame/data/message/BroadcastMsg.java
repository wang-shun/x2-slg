package com.xgame.data.message;

import java.util.List;


/**
 * 广播服务器消息
 * @author jacky.jiang
 *
 */
public class BroadcastMsg extends GateMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 内容
	 */
    private byte[] contents;
    
    /**
     * 广播消息屏蔽的玩家列表
     */
    private List<Long> ignoreSesison;
    
	public BroadcastMsg(int msgId, byte[] contents) {
		this.setMsgId(msgId);
		this.contents = contents;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public List<Long> getIgnoreSesison() {
		return ignoreSesison;
	}

	public void setIgnoreSesison(List<Long> ignoreSesison) {
		this.ignoreSesison = ignoreSesison;
	}
	
}
