package com.xgame.logic.server.game.country.entity;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

public class BulidLevelUpEventObject extends EventObject {

	public BulidLevelUpEventObject(Player player, Integer type, int oldLevel) {
		super(player, type);
		this.oldLevel = oldLevel;
	}

	protected int oldLevel;

	public long getoldLevel() {
		return this.oldLevel;
	}
}
