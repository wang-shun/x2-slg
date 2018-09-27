package com.xgame.logic.server.game.soldier.entity.model;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 解锁配件
 * @author jacky.jiang
 *
 */
public class UnlockPeijianEventObject extends GameLogEventObject {

	/**
	 * 解锁配件
	 */
	private int peijianId;
	
	public UnlockPeijianEventObject(Player player, Integer type, int peijianId) {
		super(player, type);
		this.peijianId = peijianId;
	}

	public int getPeijianId() {
		return peijianId;
	}

	public void setPeijianId(int peijianId) {
		this.peijianId = peijianId;
	}

	@Override
	public String toString() {
		return "UnlockPeijianEventObject [peijianId=" + peijianId + "]";
	}
	
}
