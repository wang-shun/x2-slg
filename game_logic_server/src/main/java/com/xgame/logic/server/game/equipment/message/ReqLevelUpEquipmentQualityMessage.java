package com.xgame.logic.server.game.equipment.message;
import com.xgame.logic.server.game.equipment.bean.EquipmentUID;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ReqLevelUpEquipmentQuality - 请求提升装备品质
 */
public class ReqLevelUpEquipmentQualityMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=300207;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=207;
	
	/***/
	@MsgField(Id = 1)
	public Integer equipmentID;
	/**装备唯一ID集合*/
	@MsgField(Id = 2)
	public List<EquipmentUID> idList = new ArrayList<EquipmentUID>();
		
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
		buf.append("idList:{");
		for (int i = 0; i < idList.size(); i++) {
			buf.append(idList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}