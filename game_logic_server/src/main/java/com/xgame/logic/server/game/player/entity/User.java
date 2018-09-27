package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8087432501878884985L;
	
	@Tag(1)
	public String userName; // 的登录游戏的账号名
	@Tag(2)
	public List<Long> roleList = new ArrayList<Long>();// 这个账号的用户列表 ,
														// 一个账号可以在每个服有个角色
	@Tag(3)
	public UserState state;// 账号状态, GM维护
	@Tag(4)
	public long uid;// 用户id
	@Tag(5)
	public long createTime; // 创建时间
	@Tag(6)
	public long loginTime; // 登录时间
	@Tag(7)
	public long logoutTime;// 登出时间
	@Tag(8)
	public long onlineRole;//当前在线角色
	@Tag(9)
	public long createRoleTime; // 创建角色时间
}
