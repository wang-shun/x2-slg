package com.xgame.logic.server.game.task.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;

/** 
 * @author ProtocolEditor
 * Task-ReqQueryActiveInfo - 获取活跃度任务列表
 */
public class ReqQueryActiveInfoMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=201203;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=203;
	

		
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