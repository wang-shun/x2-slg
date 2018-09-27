package com.xgame.framework.network.server;

public enum ServerMessage implements MessageName {
	// session message

	// session message
//	S2C_HANDSHAKE(0), //
//	S2C_SERVERINFO(1), // 服务器返回游戏服务器地址
//	S2C_USERINFO(2), // 服务器返回登录成功后的角色信息
//	S2C_COMMONMSG(3), // 服务器推送信息0 error 1 msg
//
//
//	// player message
//
//	
//	
//	S2C_PLAYERBAG(103), // 推送背包
//	S2C_USEITEM(104), // 使用道具
//	S2C_TOPICITEM(105), // 推送单个道具变动
//	S2C_CURRENT_VIEW_SPRITES(106), //3 服务器返回精灵对象集合
//	S2C_RES_SPRITE_INFO(107), //4 服务器返回资源精灵对象信息
//	S2C_OTHER_ROLE_SPRITE_INFO(108), //5 服务器返回其他玩家精灵对象信息
//	S2C_ADD_SPRITE(109), //6 服务器增加精灵对象
//	S2C_UPDATE_SPRITE(110), //7 服务器更新精灵对象
//	S2C_DEL_SPRITE(111), //8 服务器删除精灵对象
//	S2C_MAP_WALKTASK(112), //9 服务器推送当前服行军信息
//	
//	S2C_SHOP_INFO(1001), //推送商城配置信息
//
//	// role attribute
//	S2C_EXP(200), //
//	S2C_LEVEL(201), //
//	S2C_MONEY(202), //
//	S2C_BUSY(9999), // server is busy
//	//基地摆放建筑
//	S2C_ALL_COUNTRYINFO(400),
//	S2C_PLAYER_ATTRIBUTE(401),
//	S2C_LEVEL_UP(402),
//	S2C_TECH_ALL(403),
//	S2C_TECH_LEVEL_Up(404),
//	S2C_ATTRIBUTE(405),
//	S2C_CURRENCY(406),
//	S2C_TIMER(407),
//	S2C_SINGLE_TIMER(408),
//	S2C_TIME_SYSTEM(409),
//	S2C_CANCEL_TIMER_TASK(410),
//	S2C_GIVE_OUTOUT(411),
//	S2C_BUILD_REMOVE(399),
//	/* 兵工厂 */
//	/**
//	 * 兵营全部消息
//	 */
//	S2C_CAMP_INFO(510),
//	S2C_SOLDIER_CLEAR_OUTPUT(511),
//	S2C_SOLDIER_CREATE(512),
//	S2C_MOD_SOLDIER(513),		// 合成装备
//	
//	// 生物实验室模块消息编号601-650
//	S2C_DECOMPOSE_EQUIPMENT(601),	// 分解装备
//	S2C_COMPOSE_EQUIPMENT(601),		// 合成装备
//	
//	S2C_TITP_MESSAGE(602),

	;
	private int id;

	private ServerMessage(int id) {
		this.id = id;
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + id() + "]" + name();
	}
}
