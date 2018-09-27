package com.xgame.logic.server.game.equipment.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-EquipmentFragment - 
 */
public class EquipmentFragment extends XPro {
	/**材料ID*/
	@MsgField(Id = 1)
	public Integer itemID;
	/**数量*/
	@MsgField(Id = 2)
	public Integer account;
	/**位置*/
	@MsgField(Id = 3)
	public Integer position;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("itemID:" + itemID +",");
		buf.append("account:" + account +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}