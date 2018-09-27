package com.xgame.logic.server.game.country.structs.build.tach.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

public class ResearchLevelUpEventObject extends GameLogEventObject {

	/**
	 * 科技ID
	 */
	private int scienceId;
	
	/**
	 * 升级后等级
	 */
	private int newLevel;
	
	public ResearchLevelUpEventObject(Player player,int scienceId,int newLevel) {
		super(player, EventTypeConst.RESEARCH_LEVEL_UP);
		this.scienceId = scienceId;
		this.newLevel = newLevel;
	}

	public int getScienceId() {
		return scienceId;
	}

	public void setScienceId(int scienceId) {
		this.scienceId = scienceId;
	}

	public int getNewLevel() {
		return newLevel;
	}

	public void setNewLevel(int newLevel) {
		this.newLevel = newLevel;
	}
	
	
}
