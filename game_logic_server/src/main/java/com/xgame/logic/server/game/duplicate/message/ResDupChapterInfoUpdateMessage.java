package com.xgame.logic.server.game.duplicate.message;

import com.xgame.logic.server.game.duplicate.bean.DupChapterInfo;
import com.xgame.logic.server.game.duplicate.bean.DupNoteInfo;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 更新某个章节的信息消息
 */
public class ResDupChapterInfoUpdateMessage extends ResMessage{
	
	public static final int ID=2017104;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=104;
	
	/*副本节点信息*/
	@MsgField(Id = 1)
	public DupNoteInfo dupNoteInfo;
	/*副本章信息*/
	@MsgField(Id = 2)
	public DupChapterInfo dupChapterInfo;
	
	
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
		if(this.dupNoteInfo!=null) buf.append("dupNoteInfo:" + dupNoteInfo.toString() +",");
		//副本章信息
		if(this.dupChapterInfo!=null) buf.append("dupChapterInfo:" + dupChapterInfo.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}