package com.xgame.logic.server.game.email.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;

/** 
 * @author ProtocolEditor
 * Email-ReqNewEmailFlag - 请求新邮件标识
 */
public class ReqNewEmailFlagMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=410206;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=206;
	

		
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

		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}