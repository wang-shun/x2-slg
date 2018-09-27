package com.xgame.logic.server.game.country.structs.status;


/**
 *建筑物状态
 *2016-7-14  19:57:29
 *@author ye.yuan
 *
 */
public enum TimeState {
	
	NULL,
	
	/**
	 * 创建中
	 */
	CREATEING,
	
	/**
	 * 使用中(正常状态)
	 */
	USEING,
	
	/**
	 * 升级中
	 */
	LEVEL_UP,
	
	/**
	 * 移除中
	 */
	REMOVEING,
	
	/**
	 * 生产
	 */
	OUTPUT,
	
	/**
	 * 销毁
	 */
	DESTROY,
	
	/**
	 * 取消销毁
	 */
	CANCEL_DESTROY,
	
	/**
	 * 家园正在战斗
	 */
	COUNTRY_FIGHT,
	;

	TimeState() {
	}
	
}
