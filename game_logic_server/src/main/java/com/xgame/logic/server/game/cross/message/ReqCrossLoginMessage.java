package com.xgame.logic.server.game.cross.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Cross-ReqCrossLogin - 跨服登录
 */
public class ReqCrossLoginMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1005201;
	//模块号
	public static final int FUNCTION_ID=1005;
	//消息号
	public static final int MSG_ID=201;
	
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**时间戳*/
	@MsgField(Id = 2)
	public long time;
	/**验证key*/
	@MsgField(Id = 3)
	public String token;
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("time:" + time +",");
		buf.append("token:" + token +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}