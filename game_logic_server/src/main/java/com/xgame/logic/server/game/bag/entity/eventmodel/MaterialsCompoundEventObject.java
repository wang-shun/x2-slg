package com.xgame.logic.server.game.bag.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 生产材料
 * @author zehong.he
 *
 */
public class MaterialsCompoundEventObject extends GameLogEventObject {
	
	public MaterialsCompoundEventObject(Player player) {
		super(player,EventTypeConst.MATERIALS_PRODUCTION);
	}
}
