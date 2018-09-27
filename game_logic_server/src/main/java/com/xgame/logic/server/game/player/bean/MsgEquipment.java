package com.xgame.logic.server.game.player.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-MsgEquipment - 
 */
public class MsgEquipment extends XPro {
	/***/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public int equipmentId;
	/***/
	@MsgField(Id = 3)
	public boolean isEquiped;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("equipmentId:" + equipmentId +",");
		buf.append("isEquiped:" + isEquiped +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}