package com.xgame.logic.server.game.friend.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Friend-ResAddBlackList - 加入黑名单成功
 */
public class ResAddBlackListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1006104;
	//模块号
	public static final int FUNCTION_ID=1006;
	//消息号
	public static final int MSG_ID=104;
	
	/**好友信息*/
	@MsgField(Id = 1)
	public String friendList;
	/**黑名单信息*/
	@MsgField(Id = 2)
	public String blackList;
		
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
		buf.append("friendList:" + friendList +",");
		buf.append("blackList:" + blackList +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}