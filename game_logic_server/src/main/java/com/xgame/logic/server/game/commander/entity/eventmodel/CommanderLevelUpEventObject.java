package com.xgame.logic.server.game.commander.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 指挥官升级
 * @author jacky.jiang
 *
 */
public class CommanderLevelUpEventObject extends GameLogEventObject {

	public CommanderLevelUpEventObject(Player player, Integer type, int originLevel, int currentLevel) {
		super(player, type);
		this.originLevel = originLevel;
		this.currentLevel = currentLevel;
	}

	private int originLevel;

	private int currentLevel;
	
	public long getOriginLevel() {
		return this.originLevel;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public void setOriginLevel(int originLevel) {
		this.originLevel = originLevel;
	}

	@Override
	public String toString() {
		return "CommanderLevelUpEventObject [originLevel=" + originLevel
				+ ", currentLevel=" + currentLevel + ", type=" + type + "]";
	}

}
