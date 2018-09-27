package com.xgame.logic.server.game.duplicate.message;

import com.xgame.logic.server.game.duplicate.bean.PlayerDupChapterInfo;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 返回副本章信息消息
 */
public class ResDupChapterInfoMessage extends ResMessage{
	
	public static final int ID=2017101;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=101;
	
	/*副本章信息*/
	@MsgField(Id = 1)
	public PlayerDupChapterInfo playerDupChapterInfo;
	
	
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
		//副本章信息
		if(this.playerDupChapterInfo!=null) buf.append("playerDupChapterInfo:" + playerDupChapterInfo.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}