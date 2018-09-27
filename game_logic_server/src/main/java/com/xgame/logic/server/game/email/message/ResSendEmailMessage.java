package com.xgame.logic.server.game.email.message;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ResSendEmail - 发送邮件返回
 */
public class ResSendEmailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=410102;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=102;
	
	/**新的已发送邮件*/
	@MsgField(Id = 1)
	public EmailInfo emailInfo;
		
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
		buf.append("emailInfo:" + emailInfo +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}