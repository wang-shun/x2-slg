package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 资源变化
 * @author jacky.jiang
 *
 */
public class CurrencyEventObject extends GameLogEventObject {

	private CurrencyEnum currencyType;

	private boolean isIncrease;

	private long oldValue;

	private long newValue;
	
	private GameLogSource gameLogSource;

	public CurrencyEventObject(Player player , int type, CurrencyEnum currencyType, boolean isIncrease, long oldValue, long newValue, GameLogSource gameLogSource) {
		super(player , type);
		this.player = player;
		this.currencyType = currencyType;
		this.isIncrease = isIncrease;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.gameLogSource = gameLogSource;
	}

	public CurrencyEnum getCurrencyType() {
		return this.currencyType;
	}

	public boolean getIsIncrease() {
		return this.isIncrease;
	}

	public long getOldValue() {
		return this.oldValue;
	}

	public long getNewValue() {
		return this.newValue;
	}

	public GameLogSource getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(GameLogSource gameLogSource) {
		this.gameLogSource = gameLogSource;
	}

	public void setCurrencyType(CurrencyEnum currencyType) {
		this.currencyType = currencyType;
	}

	public void setIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

	public void setOldValue(long oldValue) {
		this.oldValue = oldValue;
	}

	public void setNewValue(long newValue) {
		this.newValue = newValue;
	}

	@Override
	public String toString() {
		return "CurrencyEventObject [currencyType=" + currencyType
				+ ", isIncrease=" + isIncrease + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", gameLogSource=" + gameLogSource
				+ "]";
	}
	
	
	
	
	
}
