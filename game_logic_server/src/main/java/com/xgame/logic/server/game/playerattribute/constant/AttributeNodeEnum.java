package com.xgame.logic.server.game.playerattribute.constant;


/**
 * 属性增加对象
 * @author zehong.he
 *
 */
public enum AttributeNodeEnum {
	
	/**
	 *建筑物 
	 */
	BUILD(0),

	/**
	 * 坦克
	 */
	TANK(1),
	
	/**
	 * 战车
	 */
	SUV(2),
	
	/**
	 * 飞机
	 */
	PLANE(3),
	
	/**
	 * 玩家
	 */
	PLAYER(4),
	
	/**
	 * 配件
	 */
	PEIJIAN(-1),
	;
	
	private int code;
	
	
	public static AttributeNodeEnum getCode(int code){
		for(AttributeNodeEnum o : AttributeNodeEnum.values()){
			if(o.getCode() == code){
				return o;
			}
		}
		return null;
	}
	
	private AttributeNodeEnum(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
}
