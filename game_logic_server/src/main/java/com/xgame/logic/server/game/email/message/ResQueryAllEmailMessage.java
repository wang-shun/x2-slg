package com.xgame.logic.server.game.email.message;
import com.xgame.logic.server.game.email.bean.EmailInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ResQueryAllEmail - 返回邮件列表
 */
public class ResQueryAllEmailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=410101;
	//模块号
	public static final int FUNCTION_ID=410;
	//消息号
	public static final int MSG_ID=101;
	
	/**1-邮件；2-报告；3-保存；4-已发送*/
	@MsgField(Id = 1)
	public int tag;
	/***/
	@MsgField(Id = 2)
	public List<EmailInfo> emailInfoList = new ArrayList<EmailInfo>();
	/**标签1未读邮件数量*/
	@MsgField(Id = 3)
	public int tag1Num;
	/**标签2未读邮件数量*/
	@MsgField(Id = 4)
	public int tag2Num;
	/**标签3未读邮件数量*/
	@MsgField(Id = 5)
	public int tag3Num;
	/**标签4未读邮件数量*/
	@MsgField(Id = 6)
	public int tag4Num;
		
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
		buf.append("tag:" + tag +",");
		buf.append("emailInfoList:{");
		for (int i = 0; i < emailInfoList.size(); i++) {
			buf.append(emailInfoList.get(i).toString() +",");
		}
		buf.append("tag1Num:" + tag1Num +",");
		buf.append("tag2Num:" + tag2Num +",");
		buf.append("tag3Num:" + tag3Num +",");
		buf.append("tag4Num:" + tag4Num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}