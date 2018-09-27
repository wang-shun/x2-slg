package com.xgame.logic.server.game.customweanpon.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-CustomWeaponPJ - 
 */
public class CustomWeaponPJ extends XPro {
	/***/
	@MsgField(Id = 1)
	public int pJId;
	/***/
	@MsgField(Id = 2)
	public int pos;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("pJId:" + pJId +",");
		buf.append("pos:" + pos +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}