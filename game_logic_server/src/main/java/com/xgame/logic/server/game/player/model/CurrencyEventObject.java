package com.xgame.logic.server.game.player.model;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;

public class CurrencyEventObject extends EventObject {

	protected CurrencyEnum currencyType;

	protected boolean isIncrease;

	protected long oldValue;

	protected long newValue;

	public CurrencyEventObject(Player player , Integer type, CurrencyEnum currencyType, boolean isIncrease, long oldValue,
			long newValue) {
		super(player , type);
		this.player = player;
		this.currencyType = currencyType;
		this.isIncrease = isIncrease;
		this.oldValue = oldValue;
		this.newValue = newValue;
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
}
