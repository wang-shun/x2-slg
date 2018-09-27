package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 开始采集
 * @author zehong.he
 *
 */
public class CollectSatrtEventObject extends GameLogEventObject {
	
	public CollectSatrtEventObject(Player player) {
		super(player,EventTypeConst.COLLECT_START);
	}
}
