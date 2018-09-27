package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 帐号
 * @author jacky.jiang
 *
 */
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2862034579771273837L;
	@Tag(1)
	public String userName; // 的登录游戏的账号名
	@Tag(2)
	public Map<Integer, RoleAccount> roleAccountMap = new HashMap<Integer, RoleAccount>(); // 游戏当中的角色信息
	@Tag(3)
	public UserState state;// 账号状态, GM维护
	@Tag(4)
	public long uid;// 用户id
	@Tag(5)
	public long createTime; // 创建时间
	@Tag(6)
	public long loginTime; // 登录时间
}
