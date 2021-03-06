package com.xgame.logic.server.game.equipment.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 植入体合成
 * @author zehong.he
 *
 */
public class ImplantedCompoundEventObject extends GameLogEventObject {
	
	public ImplantedCompoundEventObject(Player player) {
		super(player,EventTypeConst.IMPLANTED_COMPOUND);
	}
}
