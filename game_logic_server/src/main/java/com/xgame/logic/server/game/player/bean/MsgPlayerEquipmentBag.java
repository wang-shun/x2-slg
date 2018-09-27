package com.xgame.logic.server.game.player.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-MsgPlayerEquipmentBag - 
 */
public class MsgPlayerEquipmentBag extends XPro {
	/***/
	@MsgField(Id = 1)
	public List<MsgEquipment> equipments = new ArrayList<MsgEquipment>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("equipments:{");
		for (int i = 0; i < equipments.size(); i++) {
			buf.append(equipments.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}