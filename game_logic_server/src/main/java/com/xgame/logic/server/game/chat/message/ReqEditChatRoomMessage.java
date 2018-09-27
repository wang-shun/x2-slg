package com.xgame.logic.server.game.chat.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ReqEditChatRoom - 编辑聊天室
 */
public class ReqEditChatRoomMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1002207;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=207;
	
	/**房间key*/
	@MsgField(Id = 1)
	public String roomKey;
	/**聊天室*/
	@MsgField(Id = 2)
	public String roomName;
	/**聊天室*/
	@MsgField(Id = 3)
	public String desc;
	/**是否公开*/
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
		buf.append("roomKey:" + roomKey +",");
		buf.append("roomName:" + roomName +",");
		buf.append("desc:" + desc +",");
		buf.append("open:" + open +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}