package com.xgame.logic.server.game.chat.message;
import com.xgame.logic.server.game.chat.bean.ChatRoomMessageInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ResRoomMessage - 请求聊天室消息
 */
public class ResRoomMessageMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1002111;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=111;
	
	/**聊天房间列表*/
	@MsgField(Id = 1)
	public List<ChatRoomMessageInfo> chatRoomMessageList = new ArrayList<ChatRoomMessageInfo>();
		
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
		buf.append("chatRoomMessageList:{");
		for (int i = 0; i < chatRoomMessageList.size(); i++) {
			buf.append(chatRoomMessageList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}