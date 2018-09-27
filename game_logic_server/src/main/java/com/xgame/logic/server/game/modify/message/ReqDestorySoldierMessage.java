package com.xgame.logic.server.game.modify.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ReqDestorySoldier - 请求销毁装甲
 */
public class ReqDestorySoldierMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=601201;
	//模块号
	public static final int FUNCTION_ID=601;
	//消息号
	public static final int MSG_ID=201;
	
	/**销毁数量*/
	@MsgField(Id = 1)
	public int num;
	/**唯一id*/
	@MsgField(Id = 2)
	public long id;
		
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
		buf.append("num:" + num +",");
		buf.append("id:" + id +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}