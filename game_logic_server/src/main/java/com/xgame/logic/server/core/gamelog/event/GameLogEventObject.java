package com.xgame.logic.server.core.gamelog.event;

import com.xgame.logic.server.game.player.entity.Player;

/**
 * 日志事件记录对象
 * @author jacky.jiang
 *
 */
public class GameLogEventObject extends EventObject {

	public GameLogEventObject(Player player, Integer type, Object obj) {
		super(player, type);
		this.player = player;
		this.type = type;
		this.obj = obj;
	}

	public GameLogEventObject(Player player, Integer type) {
		super(player, type);
		this.player = player;
		this.type = type;
		this.obj = null;
	}
	
	
}
