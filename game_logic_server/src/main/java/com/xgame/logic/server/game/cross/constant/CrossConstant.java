package com.xgame.logic.server.game.cross.constant;


/**
 * 
 * @author jacky.jiang
 *
 */
public class CrossConstant {

	// 允许跨服请求的cmd列表
	public static final int[] CROSS_ALLOW_CMD= new int[]{104200, 1005201};
	
	// 跨服请求前缀
	public static final String CROSS_SERVICE_NAME = "remoteService";
}
