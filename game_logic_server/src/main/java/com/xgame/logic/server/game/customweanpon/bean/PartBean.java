package com.xgame.logic.server.game.customweanpon.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-PartBean - 配件信息
 */
public class PartBean extends XPro {
	/**配件ID*/
	@MsgField(Id = 1)
	public int partId;
	/**配件位置*/
	@MsgField(Id = 2)
	public int position;
	
	public PartBean() {
		
	}

	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("partId:" + partId +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}