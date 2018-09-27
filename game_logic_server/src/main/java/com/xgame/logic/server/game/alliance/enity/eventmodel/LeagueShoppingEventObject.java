package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 军团商店兑换
 * @author zehong.he
 *
 */
public class LeagueShoppingEventObject extends EventObject {

	public LeagueShoppingEventObject(Player player) {
		super(player, EventTypeConst.LEAGUE_SHOPPING);
	}
	
}
