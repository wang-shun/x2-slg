package com.xgame.logic.server.game.chat.message;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ResReceiveChat - 发送聊天信息
 */
public class ResReceiveChatMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1002101;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=101;
	
	/**聊天消息*/
	@MsgField(Id = 1)
	public ChatMessageInfo chatMessageInfo;
		
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
		buf.append("chatMessageInfo:" + chatMessageInfo +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}