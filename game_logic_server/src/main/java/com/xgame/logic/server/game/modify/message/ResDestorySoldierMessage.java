package com.xgame.logic.server.game.modify.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ResDestorySoldier - 请求损毁装甲返回
 */
public class ResDestorySoldierMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=601101;
	//模块号
	public static final int FUNCTION_ID=601;
	//消息号
	public static final int MSG_ID=101;
	
	/**1成功 0失败*/
	@MsgField(Id = 1)
	public int isSuccess;
	/**0已损毁1未损毁*/
	@MsgField(Id = 2)
	public int type;
	/**销毁数量*/
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
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("isSuccess:" + isSuccess +",");
		buf.append("type:" + type +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}