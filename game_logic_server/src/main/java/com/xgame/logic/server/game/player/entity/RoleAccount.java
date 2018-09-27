package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;


/**
 * 帐号角色信息
 * @author jacky.jiang
 *
 */
public class RoleAccount {

	/**
	 * 角色id
	 */
	@Tag(1)
	private long roleId;
	
	/**
	 * 创建时间
	 */
	@Tag(2)
	private long createTime;
	
	/**
	 * 联盟id
	 */
	@Tag(3)
	private String leagueName;
	
	/**
	 * 联盟名称
	 */
	@Tag(4)
	private String leagueId;

	/**
	 * 角色等级
	 */
	@Tag(5)
	private int level;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
