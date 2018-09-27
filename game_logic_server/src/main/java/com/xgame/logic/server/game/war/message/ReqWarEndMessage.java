package com.xgame.logic.server.game.war.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-ReqWarEnd - 请求战斗结束
 */
public class ReqWarEndMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=500203;
	//模块号
	public static final int FUNCTION_ID=500;
	//消息号
	public static final int MSG_ID=203;
	
	/**获胜一方id*/
	@MsgField(Id = 1)
	public long winUid;
	/**战斗id*/
	@MsgField(Id = 2)
	public long battleId;
		
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
		buf.append("winUid:" + winUid +",");
		buf.append("battleId:" + battleId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}