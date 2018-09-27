package com.xgame.logic.server.game.equipment.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ComposeEquipmentMaterial - 
 */
public class ComposeEquipmentMaterial extends XPro {
	/***/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public Integer account;
	/***/
	@MsgField(Id = 3)
	public Integer position;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("account:" + account +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}