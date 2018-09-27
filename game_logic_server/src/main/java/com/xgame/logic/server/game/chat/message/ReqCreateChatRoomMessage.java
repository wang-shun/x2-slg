package com.xgame.logic.server.game.chat.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ReqCreateChatRoom - 创建聊天室
 */
public class ReqCreateChatRoomMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1002202;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=202;
	
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**房间名称*/
	@MsgField(Id = 2)
	public String roomName;
	/**房间描述*/
	@MsgField(Id = 3)
	public String roomDesc;
	/**公开非公开*/
	@MsgField(Id = 4)
	public boolean open;
		
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
		buf.append("roomName:" + roomName +",");
		buf.append("roomDesc:" + roomDesc +",");
		buf.append("open:" + open +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}