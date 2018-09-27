package com.xgame.logic.server.game.email.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ReqSaveEmail - 保存邮件
 */
public class ReqSaveEmailMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=410205;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=205;
	
	/**邮件ID*/
	@MsgField(Id = 1)
	public long emailId;
	/**true-保存；false-取消保存*/
	@MsgField(Id = 2)
	public boolean isSave;
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("emailId:" + emailId +",");
		buf.append("isSave:" + isSave +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}