package com.xgame.logic.server.game.email.message;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ResSaveEmail - 保存邮件返回
 */
public class ResSaveEmailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=410105;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=105;
	
	/***/
	@MsgField(Id = 1)
	public EmailInfo emailInfo;
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
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("emailInfo:" + emailInfo +",");
		buf.append("isSave:" + isSave +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}