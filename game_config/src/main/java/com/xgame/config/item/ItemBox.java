package com.xgame.config.item;

import com.xgame.drop.IDrop;

/**
 *
 *2016-7-28  11:48:49
 *@author ye.yuan
 *
 */
public class ItemBox implements IDrop {
	
	private int tId;
	private int num;
	private int odds;
	
	@Override
	public int getTid() {
		return tId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getOdds() {
		return odds;
	}
	public void setOdds(int odds) {
		this.odds = odds;
	}
	
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	@Override
	public int getValue() {
		return this.odds;
	}
}
