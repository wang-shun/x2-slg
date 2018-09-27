package com.xgame.logic.server.game.commander.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 增加天赋点
 * @author jacky.jiang
 *
 */
public class AddTalentEventObject extends GameLogEventObject {
	
	public AddTalentEventObject(Player player, Integer type, int talentId) {
		super(player, type);
		this.talentId = talentId;
	}


	private int talentId;


	public int getTalentId() {
		return talentId;
	}


	public void setTalentId(int talentId) {
		this.talentId = talentId;
	}


	@Override
	public String toString() {
		return "AddTalentEventObject [talentId=" + talentId + ", type=" + type
				+ "]";
	}
	
}
