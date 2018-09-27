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
public class LoginLog extends PlayerBaseLog {
	
	/**
	 * 玩家id
	 */
	@Column(fieldType = "bigint(20)",remark = "登录时间")
	private long loginTime;
	
	@Column(fieldType = "bigint(20)",remark = "1登录2登出")
	private int loginType;

	@Override
	public TableType getTableType() {
		return TableType.DAY;
	}

	@Override
	public void logToFile() {
		
	}

}
