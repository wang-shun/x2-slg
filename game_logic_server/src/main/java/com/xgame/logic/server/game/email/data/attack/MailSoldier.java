package com.xgame.logic.server.game.email.data.attack;

import java.io.Serializable;

import io.protostuff.Tag;

/**
 *
 *2017-1-10  10:17:19
 *@author ye.yuan
 *
 */
public class MailSoldier implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private String soldierName;
	
	/**
	 * 伤兵
	 */
	@Tag(2)
	private int injuredNum;
	
	/**
	 * 报废
	 */
	@Tag(3)
	private int deathNum;
	
	/**
	 * 存活
	 */
	@Tag(4)
	private int aliveNum;
	
	/**
	 * 存活
	 */
	@Tag(5)
	private int soldierId;

	public String getSoldierName() {
		return soldierName;
	}

	public void setSoldierName(String soldierName) {
		this.soldierName = soldierName;
	}

	public int getInjuredNum() {
		return injuredNum;
	}

	public void setInjuredNum(int injuredNum) {
		this.injuredNum = injuredNum;
	}

	public int getDeathNum() {
		return deathNum;
	}

	public void setDeathNum(int deathNum) {
		this.deathNum = deathNum;
	}

	public int getAliveNum() {
		return aliveNum;
	}

	public void setAliveNum(int aliveNum) {
		this.aliveNum = aliveNum;
	}

	public int getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
	}
	
}
