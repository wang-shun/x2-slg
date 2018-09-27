package com.xgame.logic.server.game.buff.event;


/**
 * buff道具使用事件
 * @author jacky.jiang
 *
 */
public class BuffItemUseEvent {
	
	/**
	 * 道具id
	 */
	private int itemId;

	/**
	 * 获取玩家id
	 */
	private long playerId;
	
	//
	public static BuffItemUseEvent valueOf(long playerId, int itemId) {
		BuffItemUseEvent buffItemUseEvent = new BuffItemUseEvent();
		buffItemUseEvent.itemId = itemId;
		buffItemUseEvent.playerId = playerId;
		return buffItemUseEvent;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	
}
