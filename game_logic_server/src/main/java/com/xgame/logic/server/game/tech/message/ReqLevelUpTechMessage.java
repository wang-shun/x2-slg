package com.xgame.logic.server.game.tech.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Tech-ReqLevelUpTech - 
 */
public class ReqLevelUpTechMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=107200;
	//模块号
	public static final int FUNCTION_ID=107;
	//消息号
	public static final int MSG_ID=200;
	
	/***/
	@MsgField(Id = 1)
	public int sid;
	/***/
	@MsgField(Id = 2)
	public int useType;
		
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
		buf.append("sid:" + sid +",");
		buf.append("useType:" + useType +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}