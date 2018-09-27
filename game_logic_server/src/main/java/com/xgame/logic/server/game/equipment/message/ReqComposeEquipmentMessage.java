package com.xgame.logic.server.game.equipment.message;
import com.xgame.logic.server.game.equipment.bean.SubComposeEquipment;
import com.xgame.logic.server.game.equipment.bean.ComposeEquipmentMaterial;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ReqComposeEquipment - 
 */
public class ReqComposeEquipmentMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=300200;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=200;
	
	/**是否立即生产*/
	@MsgField(Id = 1)
	public boolean isImmediately;
	/**材料ID*/
	@MsgField(Id = 2)
	public Integer fragmentID;
	/**建筑id*/
	@MsgField(Id = 3)
	public Integer buildId;
	/***/
	@MsgField(Id = 4)
	public List<SubComposeEquipment> subComposeEquipments = new ArrayList<SubComposeEquipment>();
	/***/
	@MsgField(Id = 5)
	public List<ComposeEquipmentMaterial> materials = new ArrayList<ComposeEquipmentMaterial>();
		
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
		buf.append("isImmediately:" + isImmediately +",");
		buf.append("fragmentID:" + fragmentID +",");
		buf.append("buildId:" + buildId +",");
		buf.append("subComposeEquipments:{");
		for (int i = 0; i < subComposeEquipments.size(); i++) {
			buf.append(subComposeEquipments.get(i).toString() +",");
		}
		buf.append("materials:{");
		for (int i = 0; i < materials.size(); i++) {
			buf.append(materials.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}