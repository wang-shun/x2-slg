package com.xgame.logic.server.game.alliance.enity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 捐献
 * @author jacky.jiang
 *
 */
public class DonateEventObject extends GameLogEventObject {

	public DonateEventObject(Player player,int donateCount, CurrencyEnum currencyEnum,int num,int donateType) {
		super(player, EventTypeConst.ALLIANCE_DONATE);
		this.donateCount = donateCount;
		this.currencyEnum = currencyEnum;
		this.donateType = donateType;
		this.num = num;
	}
	
	/** 1军团，2军团科研*/
	private int donateType;
	
	/**
	 * 捐献次数
	 */
	private int donateCount;
	
	/**
	 * 资源类型
	 */
	private CurrencyEnum currencyEnum;
	
	/**
	 * 捐献对应资源数量
	 */
	private int num;
	
	public int getDonateCount() {
		return donateCount;
	}

	public void setDonateCount(int donateCount) {
		this.donateCount = donateCount;
	}

	public CurrencyEnum getCurrencyEnum() {
		return currencyEnum;
	}

	public void setCurrencyEnum(CurrencyEnum currencyEnum) {
		this.currencyEnum = currencyEnum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the donateType
	 */
	public int getDonateType() {
		return donateType;
	}

	/**
	 * @param donateType the donateType to set
	 */
	public void setDonateType(int donateType) {
		this.donateType = donateType;
	}
}
