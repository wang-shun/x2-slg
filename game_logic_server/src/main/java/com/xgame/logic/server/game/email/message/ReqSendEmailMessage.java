package com.xgame.logic.server.game.email.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ReqSendEmail - 发送邮件
 */
public class ReqSendEmailMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=410202;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=202;
	
	/**玩家玩家名字*/
	@MsgField(Id = 1)
	public String playerName;
	/**邮件标题*/
	@MsgField(Id = 2)
	public String title;
	/**邮件内容*/
	@MsgField(Id = 3)
	public String content;
	/**邮件类型（1普通邮件2联盟邮件3全服邮件）*/
	@MsgField(Id = 4)
	public int type;
		
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
		buf.append("playerName:" + playerName +",");
		buf.append("title:" + title +",");
		buf.append("content:" + content +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}