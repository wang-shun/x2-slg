package com.xgame.logic.server.game.timertask.entity.system.data;

/**
 * 玩家事件
 * @author jacky.jiang
 *
 */
public class GameEventTimerTaskData {
	
	/**
	 * 事件id
	 */
	private long eventId;
	
	
	public GameEventTimerTaskData(){
		
	}

	public GameEventTimerTaskData(long eventId) {
		super();
		this.eventId = eventId;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	
}
