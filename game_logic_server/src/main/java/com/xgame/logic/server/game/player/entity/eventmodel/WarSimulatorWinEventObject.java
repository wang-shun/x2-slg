package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 战争模拟器胜利
 * @author zehong.he
 *
 */
public class WarSimulatorWinEventObject extends GameLogEventObject {

	/**
	 * 1普通，2高级
	 */
	private int warType;
	/**
	 * 部署1,2,3,4 TODO
	 */
	private int step;
	
	public WarSimulatorWinEventObject(Player player,int warType) {
		super(player, EventTypeConst.WAR_SIMULATOR_WIN);
	}

	/**
	 * @return the warType
	 */
	public int getWarType() {
		return warType;
	}

	/**
	 * @param warType the warType to set
	 */
	public void setWarType(int warType) {
		this.warType = warType;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
}

