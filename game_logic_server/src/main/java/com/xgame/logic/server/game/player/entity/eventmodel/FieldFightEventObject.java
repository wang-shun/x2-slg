package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 野外战斗
 * @author zehong.he
 *
 */
public class FieldFightEventObject extends GameLogEventObject {
	
	public FieldFightEventObject(Player player) {
		super(player,EventTypeConst.FIELD_FIGHT);
	}
	
}
