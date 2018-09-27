package com.xgame.logic.server.game.equipment.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ReqBuySpecialEquipment - 请求购买万能装备
 */
public class ReqBuySpecialEquipmentMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=300209;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=209;
	
	/**万能装备ID*/
	@MsgField(Id = 1)
	public Integer equipmentID;
	/***/
	@MsgField(Id = 2)
	public Integer num;
		
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
		buf.append("equipmentID:" + equipmentID +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}