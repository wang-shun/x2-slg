package com.xgame.logic.server.game.friend.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Friend-ResSearchPlayer - 返回搜索列表
 */
public class ResSearchPlayerMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1006106;
	//模块号
	public static final int FUNCTION_ID=1006;
	//消息号
	public static final int MSG_ID=106;
	
	/**好友信息*/
	@MsgField(Id = 1)
	public List<String> searchList = new ArrayList<String>();
		
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
		buf.append("searchList:{");
		for (int i = 0; i < searchList.size(); i++) {
			buf.append(searchList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}