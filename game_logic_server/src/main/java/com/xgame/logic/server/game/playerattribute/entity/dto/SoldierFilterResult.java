package com.xgame.logic.server.game.playerattribute.entity.dto;

import java.io.Serializable;

/**
 * 筛选出征士兵结果
 * @author zehong.he
 *
 */
public class SoldierFilterResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5162036684994002253L;

	/**
	 * 士兵ID
	 */
	private long soldierId;
	
	/**
	 * 数量
	 */
	private int num;
	
	/**
	 * 总战力
	 */
	private long totalFightPower;
	
	public static SoldierFilterResult valueOf(long soldierId,int num,long totalFightPower){
		SoldierFilterResult result = new SoldierFilterResult();
		result.soldierId = soldierId;
		result.num = num;
		result.totalFightPower = totalFightPower;
		
		return result;
	}

	public long getSoldierId() {
		return soldierId;
	}

	public int getNum() {
		return num;
	}

	public long getTotalFightPower() {
		return totalFightPower;
	}
	
	
}
