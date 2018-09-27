package com.xgame.logic.server.core.gamelog.logs;

import com.xgame.logic.server.core.gamelog.annotation.Column;


/**
 * 玩家日志记录
 * @author jacky.jiang
 *
 */
public abstract class PlayerBaseLog extends BaseLog {
	
	/**
	 * 玩家id
	 */
	@Column(fieldType = "bigint(20)",remark = "玩家id")
	private long playerId;
	
	/**
	 * 角色名称
	 */
	@Column(fieldType = "varchar(50)",remark = "货币名称")
	private String roleName;
	
	/**
	 * 帐号名
	 */
	@Column(fieldType = "varchar(50)",remark = "货币名称")
	private String userName;
	
	/**
	 * 玩家基地等级
	 */
	@Column(fieldType = "int(11)",remark = "玩家基地等级")
	private int level;
	
	/**
	 * 插入数据库时间
	 */
	@Column(fieldType = "bigint", remark = "插入时间")
	public long createTime = System.currentTimeMillis();
	
	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
