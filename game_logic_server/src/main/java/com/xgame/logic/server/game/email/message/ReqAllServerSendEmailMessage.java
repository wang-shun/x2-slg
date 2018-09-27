package com.xgame.logic.server.game.email.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ReqAllServerSendEmail - 发送全服邮件
 */
public class ReqAllServerSendEmailMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=410203;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=203;
	
	/**邮件标题*/
	@MsgField(Id = 1)
	public String title;
	/**邮件内容*/
	@MsgField(Id = 2)
	public String content;
		
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
		buf.append("title:" + title +",");
		buf.append("content:" + content +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}