package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 登录登出日志
 * @author jacky.jiang
 *
 */
public class LoginEventObject extends EventObject {
	
	/**
	 * 登录
	 */
	public static final int LOGIN = 1;
	

	private long loginTime;

	public LoginEventObject(Player player, Integer type, long loginTime) {
		super(player, type);
		this.loginTime = loginTime;
	}

	public long getLoginTime() {
		return this.loginTime;
	}

	
}
