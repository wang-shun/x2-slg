package com.xgame.logic.server.game.equipment.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 植入体生产
 * @author zehong.he
 *
 */
public class ImplantedProduceEventObject extends GameLogEventObject {
	
	public ImplantedProduceEventObject(Player player) {
		super(player,EventTypeConst.IMPLANTED_PRODUCE);
	}
}
