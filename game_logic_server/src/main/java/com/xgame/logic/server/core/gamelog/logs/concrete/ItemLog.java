package com.xgame.logic.server.core.gamelog.logs.concrete;

import com.xgame.logic.server.core.gamelog.annotation.Column;
import com.xgame.logic.server.core.gamelog.annotation.Template;
import com.xgame.logic.server.core.gamelog.constant.TableType;
import com.xgame.logic.server.core.gamelog.logs.PlayerBaseLog;


/**
 * 物品日志
 * @author jacky.jiang
 *
 */
@Template(necessaryFields = "playerId")
public class ItemLog extends PlayerBaseLog {

	@Column(fieldType = "varchar(20)",remark = "物品id")
	private int itemId;
	
	@Column(fieldType = "varchar(20)",remark = "物品唯一id")
	private long userItemId;
	
	@Column(fieldType = "varchar(20)",remark = "变化之前数量")
	private int beforeNum;
	
	@Column(fieldType = "varchar(20)",remark = "变化之后的数量")
	private int currentNum;
	
	@Column(fieldType = "varchar(20)",remark = "来源")
	private String logSource;

	@Override
	public TableType getTableType() {
		return TableType.DAY;
	}

	@Override
	public void logToFile() {
		
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public long getUserItemId() {
		return userItemId;
	}

	public void setUserItemId(long userItemId) {
		this.userItemId = userItemId;
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

	public String getLogSource() {
		return logSource;
	}

	public void setLogSource(String logSource) {
		this.logSource = logSource;
	}
	
	
}
