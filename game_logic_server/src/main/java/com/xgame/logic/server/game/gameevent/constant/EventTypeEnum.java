package com.xgame.logic.server.game.gameevent.constant;

public enum EventTypeEnum {
	
	/**军团事件*/
	Group(1),
	/**个人事件*/
	Player(2),
	/**荣耀事件*/
	Honour(3);
	
	private int value;
	
	private EventTypeEnum(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
