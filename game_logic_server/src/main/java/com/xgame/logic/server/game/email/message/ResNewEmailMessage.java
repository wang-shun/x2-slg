package com.xgame.logic.server.game.email.message;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ResNewEmail - 邮件更新（收到新邮件）
 */
public class ResNewEmailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=410100;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=100;
	
	/**普通邮件*/
	@MsgField(Id = 1)
	public List<EmailInfo> normalInfos = new ArrayList<EmailInfo>();
		
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
		buf.append("normalInfos:{");
		for (int i = 0; i < normalInfos.size(); i++) {
			buf.append(normalInfos.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}