package com.xgame.logic.server.game.duplicate.message;

import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 请求副本章信息消息
 */
public class ReqGetDupChapterInfoMessage extends ReqMessage{
	
	public static final int ID=2017201;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=201;
	
	
	
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
		buf.append("]");
		return buf.toString();
	}
}