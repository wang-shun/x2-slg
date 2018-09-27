package com.xgame.logic.server.game.equipment.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-fragmentData - 
 */
public class fragmentData extends XPro {
	/***/
	@MsgField(Id = 1)
	public Integer index;
	/***/
	@MsgField(Id = 2)
	public Integer fragmentID;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("index:" + index +",");
		buf.append("fragmentID:" + fragmentID +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}