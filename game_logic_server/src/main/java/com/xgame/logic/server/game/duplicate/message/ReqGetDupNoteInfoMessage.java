package com.xgame.logic.server.game.duplicate.message;

import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 请求某个章的节点信息消息
 */
public class ReqGetDupNoteInfoMessage extends ReqMessage{
	
	public static final int ID=2017202;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=202;
	
	/*副本章ID*/
	@MsgField(Id = 1)
	public int chapterId;
	
	
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
		//副本章ID
		buf.append("chapterId:" + chapterId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}