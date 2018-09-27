package com.xgame.logic.server.game.equipment.message;
import com.xgame.logic.server.game.equipment.bean.EquipmentOutput;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ResOutputEquipment - 生产装备完成待收取
 */
public class ResOutputEquipmentMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=300116;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=116;
	
	/**装备材料产出*/
	@MsgField(Id = 1)
	public List<EquipmentOutput> equipmentOutput = new ArrayList<EquipmentOutput>();
		
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
		buf.append("equipmentOutput:{");
		for (int i = 0; i < equipmentOutput.size(); i++) {
			buf.append(equipmentOutput.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}