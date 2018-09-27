package com.xgame.logic.server.game.player.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-MsgPlayerEquipmentFragmentBag - 
 */
public class MsgPlayerEquipmentFragmentBag extends XPro {
	/***/
	@MsgField(Id = 1)
	public List<MsgEquipmentFragment> equipmentFragments = new ArrayList<MsgEquipmentFragment>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("equipmentFragments:{");
		for (int i = 0; i < equipmentFragments.size(); i++) {
			buf.append(equipmentFragments.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}