package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 矿车产量发生变化
 * @author zehong.he
 *
 */
public class MineProductUpdateEventObject extends GameLogEventObject {
	
	public MineProductUpdateEventObject(Player player) {
		super(player,EventTypeConst.MINE_PRODUCT_UPDATE);
	}
}
