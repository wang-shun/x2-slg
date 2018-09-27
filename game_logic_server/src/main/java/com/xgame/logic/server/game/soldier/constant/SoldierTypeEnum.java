package com.xgame.logic.server.game.soldier.constant;

public enum SoldierTypeEnum {

	/**
	 *坦克
	 */
	TANK(1),
	
	/**
	 * 轮式
	 */
	SUV(2),
	
	/**
	 * 飞机
	 */
	PLAIN(3)
	
	
	;
	
	private int code;
	
	private SoldierTypeEnum(int code){
		this.code = code;
	}
	
	public static SoldierTypeEnum getSoldierTypeEnum(int type){
		for(SoldierTypeEnum o : SoldierTypeEnum.values()){
			if(type == o.getCode()){
				return o;
			}
		}
		return null;
	}
	
	public int getCode(){
		return code;
	}
	
}
