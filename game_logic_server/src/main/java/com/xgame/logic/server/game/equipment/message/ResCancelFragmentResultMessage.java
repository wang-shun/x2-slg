package com.xgame.logic.server.game.equipment.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResCancelFragmentResult - 是否取消成功
 */
public class ResCancelFragmentResultMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300104;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=104;
	
	/**是否成功*/
	@MsgField(Id = 1)
	public boolean isSuccess;
	/**材料ID*/
	@MsgField(Id = 2)
	public Integer fragmentID;
	/**材料索引位置*/
	@MsgField(Id = 3)
	public Integer index;
		
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
		buf.append("fragmentID:" + fragmentID +",");
		buf.append("index:" + index +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}