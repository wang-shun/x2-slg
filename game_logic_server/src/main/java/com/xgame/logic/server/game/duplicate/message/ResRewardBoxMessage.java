package com.xgame.logic.server.game.duplicate.message;

import com.xgame.logic.server.game.duplicate.bean.DupChapterInfo;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 返回领取宝箱消息
 */
public class ResRewardBoxMessage extends ResMessage{
	
	public static final int ID=2017103;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=103;
	
	/*副本章节信息*/
	@MsgField(Id = 1)
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
		//副本章节信息
		if(this.dupChapterInfo!=null) buf.append("dupChapterInfo:" + dupChapterInfo.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}