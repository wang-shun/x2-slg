package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 头目入侵
 * @author zehong.he
 *
 */
public class ExecutiverEventObject extends GameLogEventObject {

	
	public ExecutiverEventObject(Player player) {
		super(player, EventTypeConst.LEADER_INVASION);
	}
}
