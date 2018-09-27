package com.xgame.logic.server.game.gameevent.constant;

public enum EventTaskTypeEnum {
	/**士兵生产*/
	SOLDIER_PRODUCE_TYPE(4),
	/**科研战力提升*/
	SCIENCE_FIGHT_UP_TYPE(15),
	/**城建战力提升*/
	BUILDING_FIGHT_UP_TYPE(18),
	/**占领领土*/
	TERRIROTY_TYPE(22),
	/**采集*/
	COLLECT_TYPE(24),
	/**损伤敌方士兵*/
	HURT_SOLDIER_TYPE(31),
	/**摧毁敌方士兵*/
	DESTORY_SOLDIER_TYPE(32),
	/**模拟器胜利*/
	SIMULATOR_TYPE(41);
	
	private int value;
	
	private EventTaskTypeEnum(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
