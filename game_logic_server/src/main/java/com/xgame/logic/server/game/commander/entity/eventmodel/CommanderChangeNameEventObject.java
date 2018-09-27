package com.xgame.logic.server.game.commander.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

public class CommanderChangeNameEventObject extends GameLogEventObject {
	
	protected String oldName;
	
	public CommanderChangeNameEventObject(Player player , Integer type, String oldName) {
		super(player , type);
		this.oldName = oldName;
	}
	
	public String getOldName() {
		return this.oldName;
	}

	@Override
	public String toString() {
		return "CommanderChangeNameEventObject [oldName=" + oldName + ", type="
				+ type + "]";
	}
	
}
