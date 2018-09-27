package com.xgame.logic.server.game.chat.entity;

import io.protostuff.Tag;


/**
 * 聊天内容
 * @author jacky.jiang
 *
 */
public class ChatMessage {
	
	/**
	 * 发送方玩家id
	 */
	@Tag(1)
	private long sendPlayerId;
	
	/**
	 * 接收方玩家id
	 */
	@Tag(2)
	private String target;
	
	/**
	 * 聊天内容
	 */
	@Tag(3)
	private String content;
	
	/**
	 * 频道
	 */
	@Tag(4)
	private int channel;
	
	/**
	 * 发送时间
	 */
	@Tag(5)
	private long sendTime;
	
	/**
	 * 来源
	 */
	@Tag(6)
	private String source;
	
	/**
	 * 消息类型
	 */
	@Tag(7)
	private int messageType;
	
	public long getSendPlayerId() {
		return sendPlayerId;
	}

	public void setSendPlayerId(long sendPlayerId) {
		this.sendPlayerId = sendPlayerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
