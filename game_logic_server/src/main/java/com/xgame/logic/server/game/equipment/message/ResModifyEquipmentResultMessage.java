package com.xgame.logic.server.game.equipment.message;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResModifyEquipmentResult - 修改装备的状态
 */
public class ResModifyEquipmentResultMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300106;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=106;
	
	/**装备唯一ID*/
	@MsgField(Id = 1)
	public long id;
	/**装备ID*/
	@MsgField(Id = 2)
	public Integer equipmentId;
	/**是否被穿戴*/
	@MsgField(Id = 3)
	public boolean isEquiped;
	/**是否增加*/
	@MsgField(Id = 4)
	public boolean isAdd;
		
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
		buf.append("id:" + id +",");
		buf.append("equipmentId:" + equipmentId +",");
		buf.append("isEquiped:" + isEquiped +",");
		buf.append("isAdd:" + isAdd +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}