package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 侦查玩家(时间队列开始前的时间)
 * @author zehong.he
 *
 */
public class ScoutEventObject extends GameLogEventObject {

	
	public ScoutEventObject(Player player) {
		super(player,  EventTypeConst.SCOUT);
	}

}

