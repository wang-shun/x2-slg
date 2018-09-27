package com.xgame.logic.server.game.notice.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Notice-ResTitle - 语言头消息
 */
public class ResTitleMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1004100;
	//模块号
	public static final int FUNCTION_ID=1004;
	//消息号
	public static final int MSG_ID=100;
	
	/**多语言id*/
	@MsgField(Id = 1)
	public int languageId;
	/**返回list参数*/
	@MsgField(Id = 2)
	public String param;
		
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
		buf.append("languageId:" + languageId +",");
		buf.append("param:" + param +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}