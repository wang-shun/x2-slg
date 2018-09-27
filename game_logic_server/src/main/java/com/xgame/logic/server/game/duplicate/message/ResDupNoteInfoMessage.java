package com.xgame.logic.server.game.duplicate.message;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.duplicate.bean.DupNoteInfo;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 返回某个章的节点信息消息
 */
public class ResDupNoteInfoMessage extends ResMessage{
	
	public static final int ID=2017102;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=102;
	
	/*副本节点信息*/
	@MsgField(Id = 1)
	public List<DupNoteInfo> dupNoteInfoList = new ArrayList<DupNoteInfo>();
	
	
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
		//副本节点信息
		buf.append("dupNoteInfoList:{");
		for (int i = 0; i < dupNoteInfoList.size(); i++) {
			buf.append(dupNoteInfoList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}