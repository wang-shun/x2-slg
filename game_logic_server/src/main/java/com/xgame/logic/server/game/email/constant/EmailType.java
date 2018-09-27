package com.xgame.logic.server.game.email.constant;

/**
 * 信函类型
 */
public interface EmailType {
	
	int ALLPLAYER = 0;
	
	/** 系统信函 (个人收到)*/
	int SYSTEM = 1;
	
	/** 公会信函（公会所有成员收到） */
	int ALLIANCE = 2;
	
	/** 玩家邮件 (个人收到)*/
	int PLAYER = 3;
	
	/** 战斗报告(个人收到)*/
	int REPORT = 4;
}
