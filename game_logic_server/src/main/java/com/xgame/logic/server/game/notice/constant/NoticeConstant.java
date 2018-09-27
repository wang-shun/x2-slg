package com.xgame.logic.server.game.notice.constant;

/**
 * 公告
 * @author jacky.jiang
 *
 */
public interface NoticeConstant {

	/**
	 * 锻造装备
	 */
	int EQUIPMENT_COMPOSE = 1001;
	
	/**
	 * 指挥官升级
	 */
	int COMMAND_LEVELUP = 1002;
	
	/**
	 * 玩家大喇叭公告
	 */
	int PLAYER_TYPON = 1003;
	
	/**
	 * 创建联盟公告
	 * XXX创建了XXX军团
	 */
	int CREATE_ALLIANCE = 1004;
	
	/**
	 * 军团公告玩家升级成功
	 * 升级成功 需要世界公告：XXX军团成功升级到N，有位置了。我要加入
	 */
	int ALLIANCE_LEVELUP = 1005;
	
	/**
	 * 捐献资源
	 * XXX为军团捐献了XXX资源
	 */
	int NOTICE_ALLIANCE_DOATE_RESOURCE = 1006;
	
	/**
	 * 捐献钻石
	 * XXX为军团捐献了XX钻石
	 */
	int NOTICE_ALLIANCE_DOATE_DIAMOND = 1007;
	
	/**
	 * 转换军团长
	 * XXX将XXX任命为新的XXXX。
	 */
	int NOTICE_CHANGE_LEADER = 1008;
	
	/**
	 * 军团招募公告
	 */
	int RECRUIT_ALIANCE_MEMBER = 1009;
	
	/**
	 * XXX将XXX任命为新的XXXX
	 */
	int SET_ALLIANCE_RANK = 1010;
	
	/**
	 * XXX对XXX发起了集结进攻
	 */
	int TEAM_ATTACK = 1011;
}
