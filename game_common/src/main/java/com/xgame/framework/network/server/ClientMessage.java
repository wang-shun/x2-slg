package com.xgame.framework.network.server;

public enum ClientMessage implements MessageName {

//	// common
	C2S_HEARTBEAT(0), //
	C2S_GETSEERVER(1), //
//	C2S_CONNECTGAMESERVER(2), //
//	C2S_SERVER_TIME(3), //
//	C2S_REJOIN(10), //
//	C2S_OFFLINE(999), //
//	
//	C2S_GM(99999),	//GM调试
//	// all session command should have message id < 100
//
//	C2S_LOGIN(100), //
//	C2S_GETVIEWSPRITES(101), // 请求当前位置所有精灵对象(c2s:101)
//	C2S_GETSPRITEINFO(102), // 请求精灵信息(c2s:102)
//
//	C2S_PLAYERBAG(103), // 请求背包列表
//	C2S_USEITEM(104), // 使用道具
//	C2S_BUYITEM(105), // 购买道具
//	C2S_SHOPINFO(106), // 商店信息
//	
//	C2S_GETAWATD(1011), //领取领奖中心的奖励
//	
//	// chat
//	C2S_CHAT_CHANNEL(1000), //
//	C2S_CHAT_TALK(10001), //
//	// the end
//	
//	C2S_BUILD_CREATE(400),
//	C2S_BUILD_MOVE(401),
//	C2S_BUILD_EDIT(402),
//	C2S_BUILD_REMOVE(403),
//	C2S_BUILD_LEVEL(404),
//	C2S_TECH_LEVEL_UP(405),
//	C2S_BUILD_GIVE(406),
//	
//	/**
//	 * 取消定时器任务队列
//	 */
//	C2S_CANCEL_TIMER_TASK(504),
//	
//	/**
//	 * 使用加速
//	 */
//	C2S_SPEED_TIMER_TASK(505),
//	
//	/* 兵工厂 */
//	/**
//	 * 生产或销毁
//	 */
//	C2S_OUTPUT_SOLDIER(501),
//
//	/**
//	 * 创建  销毁
//	 */
//	C2S_SOLDIER_CLEAR(503),
//	
//	/**
//	 * 创建  销毁
//	 */
//	C2S_DIAMOND_SUB_SPEED(506),
//	
//	/**
//	 *  生物实验室模块消息编号2001 至 2050
//	 */
//	C2S_DECOMPOSE_EQUIPMENT(2001),	// 分解装备
//	C2S_COMPOSE_EQUIPMENT(2002),	// 合成装备
//	C2S_FORGING_EQUIPMENT(2003),	// 锻造装备
	;
	private int id;

	private ClientMessage(int id) {
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
