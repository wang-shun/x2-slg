package com.xgame.logic.server.core.gamelog.logs.concrete;

import com.xgame.logic.server.core.gamelog.annotation.Column;
import com.xgame.logic.server.core.gamelog.annotation.Template;
import com.xgame.logic.server.core.gamelog.constant.TableType;
import com.xgame.logic.server.core.gamelog.logs.PlayerBaseLog;


/**
 * 操作日志
 * @author jacky.jiang
 *
 */
@Template(necessaryFields = "playerId")
public class OperatorLog extends PlayerBaseLog{

	
	@Column(fieldType = "longtext", remark = "操作内容")
	private String content;
	
	@Column(fieldType = "int(11)",remark = "操作类型")
	private int type;

	@Override
	public TableType getTableType() {
		return TableType.DAY;
	}

	@Override
	public void logToFile() {
		
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
