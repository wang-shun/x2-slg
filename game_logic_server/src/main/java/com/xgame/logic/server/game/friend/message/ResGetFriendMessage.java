package com.xgame.logic.server.game.friend.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Friend-ResGetFriend - 返回关系列表
 */
public class ResGetFriendMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1006102;
	//模块号
	public static final int FUNCTION_ID=1006;
	//消息号
	public static final int MSG_ID=102;
	
	/**好友信息*/
	@MsgField(Id = 1)
	public List<String> friendList = new ArrayList<String>();
	/**黑名单列表*/
	@MsgField(Id = 2)
	public List<String> blackList = new ArrayList<String>();
		
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
		buf.append("friendList:{");
		for (int i = 0; i < friendList.size(); i++) {
			buf.append(friendList.get(i).toString() +",");
		}
		buf.append("blackList:{");
		for (int i = 0; i < blackList.size(); i++) {
			buf.append(blackList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}