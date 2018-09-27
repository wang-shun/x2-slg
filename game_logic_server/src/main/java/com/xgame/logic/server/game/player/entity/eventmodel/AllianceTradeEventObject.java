package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 联盟交易
 * @author jacky.jiang
 *
 */
public class AllianceTradeEventObject extends GameLogEventObject {

	private long targetPlayerId;
	
	/**
	 * 石油
	 */
	private int oil;
	
	/**
	 * 钢铁
	 */
	private int steel;
	
	/**
	 * 稀土
	 */
	private int rare;
	
	/**
	 * 黄金
	 */
	private int gold;
	
	
	public AllianceTradeEventObject(Player player, Integer type) {
		super(player, type);
	}


	public long getTargetPlayerId() {
		return targetPlayerId;
	}


	public void setTargetPlayerId(long targetPlayerId) {
		this.targetPlayerId = targetPlayerId;
	}


	public int getOil() {
		return oil;
	}


	public void setOil(int oil) {
		this.oil = oil;
	}


	public int getSteel() {
		return steel;
	}


	public void setSteel(int steel) {
		this.steel = steel;
	}


	public int getRare() {
		return rare;
	}


	public void setRare(int rare) {
		this.rare = rare;
	}


	public int getGold() {
		return gold;
	}


	public void setGold(int gold) {
		this.gold = gold;
	}


	@Override
	public String toString() {
		return "AllianceTradeEventObject [targetPlayerId=" + targetPlayerId
				+ ", oil=" + oil + ", steel=" + steel + ", rare=" + rare
				+ ", gold=" + gold + ", type=" + type + "]";
	}
	
}

