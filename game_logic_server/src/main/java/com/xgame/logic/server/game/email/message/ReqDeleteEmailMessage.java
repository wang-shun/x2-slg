package com.xgame.logic.server.game.email.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ReqDeleteEmail - 删除邮件
 */
public class ReqDeleteEmailMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=410203;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=203;
	
	/**删除类型 true 删除收件,false 删除已发*/
	@MsgField(Id = 1)
	public boolean deleteType;
	/**邮件ID*/
	@MsgField(Id = 2)
	public List<Long> idList = new ArrayList<Long>();
		
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
		buf.append("deleteType:" + deleteType +",");
		buf.append("idList:{");
		for (int i = 0; i < idList.size(); i++) {
			buf.append(idList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}