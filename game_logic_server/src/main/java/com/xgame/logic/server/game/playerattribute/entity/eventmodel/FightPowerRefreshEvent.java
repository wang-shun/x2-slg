package com.xgame.logic.server.game.playerattribute.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 战力发生变化
 * @author jacky.jiang
 *
 */
public class FightPowerRefreshEvent extends GameLogEventObject  {
	
	/**
	 * 老战力
	 */
	private long oldFightPower;
	
	/**
	 * 当前总战力
	 */
	private long fightPower;
	
	/**
	 * 来源信息
	 */
	private GameLogSource gameLogSource;


	public FightPowerRefreshEvent(Player player, int type, long oldFightPower, long fightPower, GameLogSource gameLogSource) {
		super(player, type);
		this.player = player;
		this.fightPower = fightPower;
		this.oldFightPower = oldFightPower;
		this.gameLogSource = gameLogSource;
	}
	
	
	public long getOldFightPower() {
		return oldFightPower;
	}

	public void setOldFightPower(long oldFightPower) {
		this.oldFightPower = oldFightPower;
	}

	public long getFightPower() {
		return fightPower;
	}

	public void setFightPower(long fightPower) {
		this.fightPower = fightPower;
	}
	
	public GameLogSource getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(GameLogSource gameLogSource) {
		this.gameLogSource = gameLogSource;
	}
	

	@Override
	public String toString() {
		return "FightPowerRefreshEvent [oldFightPower=" + oldFightPower
				+ ", fightPower=" + fightPower + ", gameLogSource="
						+ gameLogSource + "]";
	}

	
}
