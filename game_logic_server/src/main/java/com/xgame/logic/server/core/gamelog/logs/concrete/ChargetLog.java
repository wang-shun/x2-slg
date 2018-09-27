package com.xgame.logic.server.core.gamelog.logs.concrete;

import com.xgame.logic.server.core.gamelog.annotation.Column;
import com.xgame.logic.server.core.gamelog.annotation.Template;
import com.xgame.logic.server.core.gamelog.constant.TableType;
import com.xgame.logic.server.core.gamelog.logs.PlayerBaseLog;



/**
 * 充值日志
 * @author jacky.jiang
 *
 */
@Template(necessaryFields = "serverId")
public class ChargetLog extends PlayerBaseLog {

	/** 充值运营商 */	
	@Column(fieldType = "int(11)",remark = "运营商")
	private int operator;
	/** 充值运营商 */
	@Column(fieldType = "int(11)",remark = "服标识")
	private int serverId;
	/** 充值订单号 */
	@Column(fieldType = "varchar(100)",remark = "订单号")
	private String orderNO;
	/** 充值金额 */
	@Column(fieldType = "int(11)",remark = "充值金额")
	private int money;
	/** 充值得到的金币 */
	@Column(fieldType = "int(11)",remark = "充值得到的金币")
	private int gold;
	/** 充值人民币 */
	@Column(fieldType = "int(11)",remark = "充值人民币")
	private double rmb;
	/** 充值订单状态 */
	@Column(fieldType = "varchar(20)",remark = "充值订单状态 ")
	private String chargeStatus;
	/** 货币名称 */
	@Column(fieldType = "varchar(20)",remark = "货币名称")
	private String currenyName;
	/** 完成充值时间 */
	@Column(fieldType = "bigint(20)",remark = "完成充值时间")
	private long completeTime;
	/** 充值完成时间 */
	@Column(fieldType = "bigint(20)",remark = "创建时间")
	private long createTime;
	/** 货币类型 */
	@Column(fieldType = "varchar(20)",remark = "货币类型")
	private String currency = "CNY";
	
	
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public double getRmb() {
		return rmb;
	}
	public void setRmb(double rmb) {
		this.rmb = rmb;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	
	public String getCurrenyName() {
		return currenyName;
	}
	
	public void setCurrenyName(String currenyName) {
		this.currenyName = currenyName;
	}
	public long getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(long completeTime) {
		this.completeTime = completeTime;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override
	public TableType getTableType() {
		return TableType.SINGLE;
	}
	
	@Override
	public void logToFile() {
		
	}
}
