package com.xgame.logic.server.core.gamelog.logs.concrete;

import com.xgame.logic.server.core.gamelog.annotation.Column;
import com.xgame.logic.server.core.gamelog.annotation.Template;
import com.xgame.logic.server.core.gamelog.constant.TableType;
import com.xgame.logic.server.core.gamelog.logs.PlayerBaseLog;


/**
 * 士兵日志
 * @author jacky.jiang
 *
 */
@Template(necessaryFields = "playerId")
public class SoldierLog extends PlayerBaseLog {

	@Column(fieldType = "bigint(20)",remark = "士兵唯一id")
	private long soldierId;
	
	@Column(fieldType = "int(11)",remark = "变化之前数量")
	private int beforeNum;
	
	@Column(fieldType = "int(11)",remark = "变化之后的数量")
	private int currentNum;
	
	/**
	 * 出征类型
	 */
	@Column(fieldType = "int(11)",remark = "出征类型")
	private int marchType;
	
	/**
	 * 行军状态
	 */
	@Column(fieldType = "int(11)",remark = "行军状态")
	private int marchState;
	
	/**
	 * 来源信息
	 */
	@Column(fieldType = "int(11)",remark = "来源")
	private int gameLogSource;
	
	/**
	 * 来源信息
	 */
	@Column(fieldType = "int(11)",remark = "士兵类型")
	private int soldierType;
	
	@Override
	public TableType getTableType() {
		return TableType.DAY;
	}

	@Override
	public void logToFile() {
		
	}

	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
	}

	public int getBeforeNum() {
		return beforeNum;
	}

	public void setBeforeNum(int beforeNum) {
		this.beforeNum = beforeNum;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public int getMarchType() {
		return marchType;
	}

	public void setMarchType(int marchType) {
		this.marchType = marchType;
	}

	public int getMarchState() {
		return marchState;
	}

	public void setMarchState(int marchState) {
		this.marchState = marchState;
	}

	public int getGameLogSource() {
		return gameLogSource;
	}

	public void setGameLogSource(int gameLogSource) {
		this.gameLogSource = gameLogSource;
	}

	public int getSoldierType() {
		return soldierType;
	}

	public void setSoldierType(int soldierType) {
		this.soldierType = soldierType;
	}

	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long soldierId) {
		this.soldierId = soldierId;
	}
	
}
