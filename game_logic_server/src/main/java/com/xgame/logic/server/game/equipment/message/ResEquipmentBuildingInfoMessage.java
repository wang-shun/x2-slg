package com.xgame.logic.server.game.equipment.message;
import com.xgame.logic.server.game.equipment.bean.EquipmentFragment;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResEquipmentBuildingInfo - 正在锻造的装备所需的材料
 */
public class ResEquipmentBuildingInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300115;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=115;
	
	/**目标装备ID*/
	@MsgField(Id = 1)
	public Integer EquipmentID;
	/**装备模版ID集合*/
	@MsgField(Id = 2)
	public List<Integer> EquipmentIDs = new ArrayList<Integer>();
	/**材料id和数量*/
	@MsgField(Id = 3)
	public List<EquipmentFragment> EquipmentFragments = new ArrayList<EquipmentFragment>();
		
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
		buf.append("EquipmentID:" + EquipmentID +",");
		buf.append("EquipmentIDs:{");
		for (int i = 0; i < EquipmentIDs.size(); i++) {
			buf.append(EquipmentIDs.get(i).toString() +",");
		}
		buf.append("EquipmentFragments:{");
		for (int i = 0; i < EquipmentFragments.size(); i++) {
			buf.append(EquipmentFragments.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}