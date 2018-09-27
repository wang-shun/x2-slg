package com.xgame.logic.server.game.alliance.enity;

/**
 * 联盟详情
 * @author jacky.jiang
 *
 */
public class AllianceBattleDetail {
	
	/**获得金币数量*/
	private int moneyNum;
	/**获得稀土数量*/
	private int earthNum;
	/**获得石油数量*/
	private int oilNum;
	/**获得钢材数量*/
	private int steelNum;
	/** 防守建筑血量损失*/
	private int defendDestroy;
	
	public int getMoneyNum() {
		return moneyNum;
	}
	public void setMoneyNum(int moneyNum) {
		this.moneyNum = moneyNum;
	}
	public int getEarthNum() {
		return earthNum;
	}
	public void setEarthNum(int earthNum) {
		this.earthNum = earthNum;
	}
	public int getOilNum() {
		return oilNum;
	}
	public void setOilNum(int oilNum) {
		this.oilNum = oilNum;
	}
	public int getSteelNum() {
		return steelNum;
	}
	public void setSteelNum(int steelNum) {
		this.steelNum = steelNum;
	}
	public int getDefendDestroy() {
		return defendDestroy;
	}
	public void setDefendDestroy(int defendDestroy) {
		this.defendDestroy = defendDestroy;
	}
	
}
