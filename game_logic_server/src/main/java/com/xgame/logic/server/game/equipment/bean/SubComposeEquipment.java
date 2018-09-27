package com.xgame.logic.server.game.equipment.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-SubComposeEquipment - 
 */
public class SubComposeEquipment extends XPro {
	/***/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public boolean isTarget;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("isTarget:" + isTarget +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}