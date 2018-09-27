package com.xgame.logic.server.game.war.constant;

public class WarConstant {
	
	// 战斗线程核心大小
	public static final int CORE_THREAD_SIZE = 20;
	// 线程吃最大小
	public static final int MAX_THREAD_SIZE = Integer.MAX_VALUE;
	// 回收线程频率
	public static final int KEEP_ALIVE_MILLIS = 60000;
	
	public static final String THREAD_GROUP_NAME = "战斗线程池";

	/**
	 * 服务器按按照每秒30帧 计算战斗(时间秒 * 30 == 循环次数)
	 */
	public static final int FPS = 30;
	
	/**
	 * 战斗总共时长
	 */
	public static final int FIGHT_TIME = 3 * 60;
	
}
