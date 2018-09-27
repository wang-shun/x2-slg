package com.xgame.logic.server.game.timertask.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * vip加速
 * @author jacky.jiang
 *
 */
public class VipSpeedupEventObject extends GameLogEventObject {

	/**
	 * 玩家id
	 */
	private long playerId;
	
	/**
	 * 加速时间
	 */
	private int speedupTime;
	

	public VipSpeedupEventObject(Player player, int type, int speedupTime) {
		super(player, type);
		this.speedupTime = speedupTime;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getSpeedupTime() {
		return speedupTime;
	}

	public void setSpeedupTime(int speedupTime) {
		this.speedupTime = speedupTime;
	}

	@Override
	public String toString() {
		return "VipSpeedupEventObject [playerId=" + playerId + ", speedupTime="
				+ speedupTime + "]";
	}
	
	
}
