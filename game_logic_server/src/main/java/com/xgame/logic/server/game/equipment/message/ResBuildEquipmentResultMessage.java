package com.xgame.logic.server.game.equipment.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResBuildEquipmentResult - 是否开始锻造成功
 */
public class ResBuildEquipmentResultMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300107;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=107;
	
	/**是否成功*/
	@MsgField(Id = 1)
	public boolean isSuccess;
	/**是否立即完成*/
	@MsgField(Id = 2)
	public boolean isImmediately;
		
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
		buf.append("isImmediately:" + isImmediately +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}