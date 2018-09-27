package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 生产合成
 * @author zehong.he
 *
 */
public class MaterialsProductionEventObject extends GameLogEventObject {
	
	public MaterialsProductionEventObject(Player player) {
		super(player,EventTypeConst.MATERIALS_COMPOUND);
	}
}
