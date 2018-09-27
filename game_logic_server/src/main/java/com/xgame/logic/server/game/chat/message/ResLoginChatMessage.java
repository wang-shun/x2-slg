package com.xgame.logic.server.game.chat.message;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.logic.server.game.chat.bean.ChatPlayerMessageInfo;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ResLoginChat - 登录查询消息
 */
public class ResLoginChatMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1002110;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=110;
	
	/**玩家离线消息数量*/
	@MsgField(Id = 1)
	public int playerMessageSize;
	/**聊天室消息数量*/
	@MsgField(Id = 2)
	public int roomMessageSize;
	/**全服消息列表*/
	@MsgField(Id = 3)
	public List<ChatMessageInfo> worldMessageList = new ArrayList<ChatMessageInfo>();
	/**私聊列表*/
	@MsgField(Id = 4)
	public List<ChatPlayerMessageInfo> chatPlayerMessageList = new ArrayList<ChatPlayerMessageInfo>();
	/**军团消息列表*/
	@MsgField(Id = 5)
	public List<ChatMessageInfo> allianceMessageList = new ArrayList<ChatMessageInfo>();
		
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
		buf.append("playerMessageSize:" + playerMessageSize +",");
		buf.append("roomMessageSize:" + roomMessageSize +",");
		buf.append("worldMessageList:{");
		for (int i = 0; i < worldMessageList.size(); i++) {
			buf.append(worldMessageList.get(i).toString() +",");
		}
		buf.append("chatPlayerMessageList:{");
		for (int i = 0; i < chatPlayerMessageList.size(); i++) {
			buf.append(chatPlayerMessageList.get(i).toString() +",");
		}
		buf.append("allianceMessageList:{");
		for (int i = 0; i < allianceMessageList.size(); i++) {
			buf.append(allianceMessageList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}