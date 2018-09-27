package com.xgame.logic.server.game.equipment.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-EquipmentOutput - 装备ID产出
 */
public class EquipmentOutput extends XPro {
	/**材料id*/
	@MsgField(Id = 1)
	public Integer equipmentId;
	/**建筑id*/
	@MsgField(Id = 2)
	public Integer uid;
	/**产出类型（1材料2装备））*/
	@MsgField(Id = 3)
	public Integer outputType;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("equipmentId:" + equipmentId +",");
		buf.append("uid:" + uid +",");
		buf.append("outputType:" + outputType +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}