package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 联盟升级
 * @author jacky.jiang
 *
 */
public class LevelupAllianceEventObject extends GameLogEventObject {

	public LevelupAllianceEventObject(Player player, int type, int beforeLevel, int currentLevel) {
		super(player, type);
		this.beforeLevel = beforeLevel;
		this.currentLevel = currentLevel;
	}
	
	private int beforeLevel;
	
	private int currentLevel;

	public int getBeforeLevel() {
		return beforeLevel;
	}

	public void setBeforeLevel(int beforeLevel) {
		this.beforeLevel = beforeLevel;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

}
