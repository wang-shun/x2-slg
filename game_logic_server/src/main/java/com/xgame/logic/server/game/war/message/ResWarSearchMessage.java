package com.xgame.logic.server.game.war.message;
import com.xgame.logic.server.game.war.bean.WarDefendData;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-ResWarSearch - 返回搜索玩家
 */
public class ResWarSearchMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=500101;
	//模块号
	public static final int FUNCTION_ID=500;
	//消息号
	public static final int MSG_ID=101;
	
	/**搜索到的玩家数据*/
	@MsgField(Id = 1)
	public WarDefendData defenData;
		
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		return "s2s";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public CommandEnum getCommandEnum() {
		return Communicationable.CommandEnum.PLAYERMSG;
	}
	
	@Override
	public HandlerEnum getHandlerEnum() {
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("defenData:" + defenData +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}