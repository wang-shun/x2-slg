package com.xgame.logic.server.game.chat.constant;


/**
 * 聊天频道类型
 * @author jacky.jiang
 *
 */
public enum ChatChannelType {
	
	/**
	 * 私聊
	 */
	PRIVATENESS(1),
	
	/**
	 * 世界聊天
	 */
	WORLD(2),
	
	/**
	 * 军团聊天
	 */
	ALLIANCE(3),
	
	/**
	 * 聊天室
	 */
	CHAT_ROOM(4),
	
	/**
	 * 大喇叭
	 */
	TYPHON(5)
	;
	
	private int type;
	
	ChatChannelType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
