package com.xgame.logic.server.game.armshop.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-ReqCampDesitory - 注释
 */
public class ReqCampDesitoryMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=101300;
	//模块号
	public static final int FUNCTION_ID=101;
	//消息号
	public static final int MSG_ID=300;
	
	/**唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/***/
	@MsgField(Id = 2)
	public int campType;
	/***/
	@MsgField(Id = 3)
	public int num;
		
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
		buf.append("soldierId:" + soldierId +",");
		buf.append("campType:" + campType +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}