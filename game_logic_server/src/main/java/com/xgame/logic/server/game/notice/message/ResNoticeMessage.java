package com.xgame.logic.server.game.notice.message;
import com.xgame.logic.server.game.notice.bean.NoticeBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Notice-ResNotice - 返回公告信息
 */
public class ResNoticeMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1004101;
	//模块号
	public static final int FUNCTION_ID=1004;
	//消息号
	public static final int MSG_ID=101;
	
	/**公告*/
	@MsgField(Id = 1)
	public NoticeBean noticeBean;
		
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
		buf.append("noticeBean:" + noticeBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}