package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 点击建筑收获
 * @author zehong.he
 *
 */
public class BuildingRewardEventObject extends GameLogEventObject {

	
	public BuildingRewardEventObject(Player player) {
		super(player, EventTypeConst.BUILDING_REWARD);
	}
}
