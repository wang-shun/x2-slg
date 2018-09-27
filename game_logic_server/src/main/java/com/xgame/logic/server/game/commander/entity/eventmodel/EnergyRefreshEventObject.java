package com.xgame.logic.server.game.commander.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 体力刷新
 * @author jacky.jiang
 *
 */
public class EnergyRefreshEventObject extends GameLogEventObject {

	public EnergyRefreshEventObject(Player player, Integer type, int beforeEnergy, int currentEnergy) {
		super(player, type);
		this.beforeEnergy = beforeEnergy;
		this.currentEnergy = currentEnergy;
	}

	/**
	 * 
	 */
	private int beforeEnergy;

	private int currentEnergy;

	public int getBeforeEnergy() {
		return beforeEnergy;
	}

	public void setBeforeEnergy(int beforeEnergy) {
		this.beforeEnergy = beforeEnergy;
	}

	public int getCurrentEnergy() {
		return currentEnergy;
	}

	public void setCurrentEnergy(int currentEnergy) {
		this.currentEnergy = currentEnergy;
	}

	@Override
	public String toString() {
		return "EnergyRefreshEventObject [beforeEnergy=" + beforeEnergy
				+ ", currentEnergy=" + currentEnergy + "]";
	}
	
}
