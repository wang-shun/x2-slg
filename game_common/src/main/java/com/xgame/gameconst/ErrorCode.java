package com.xgame.gameconst;

public class ErrorCode {

	public static final int TYPE_ERROR = 0;
	public static final int TYPE_MSG = 1;

	// FIXME 改成读配置
	public static final String ROLE_EMPTY = "角色数据为空"; // 通过roleid无法在db中找到role对象
	public static final String CUR_SERVER_HAS_ROLE = "当前服已有角色";
	public static final String USER_EMPTY = "用户数据为空";
	public static final String CREATE_ROLE_TOO_FAST = "角色创建时间间隔要大于24小时";
	public static final String USER_DISABLE = "账号维护中,无法登陆";
	public static final String USER_BUSY = "角色数据存储中,请稍后登陆";
	public static final String USER_LOGIN_TOO_FAST = "登录过于频繁";
	public static final String USER_LOGINING = "用户登录中";
	public static final String USER_GAMEING = "用户还未下线";
	public static final String DB_ERROR = "数据库维护中";
}
