package com.xgame.common;
/**
 *
 *2016-8-18  17:39:23
 *@author ye.yuan
 *
 */
public class ItemConf {

	private int tId;
	

	private int num;
	
	
	public ItemConf(int itemId, int num) {
		super();
		this.tId = itemId;
		this.num = num;
	}

	public int getTid() {
		return tId;
	}

	public void setItemId(int itemId) {
		this.tId = itemId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
