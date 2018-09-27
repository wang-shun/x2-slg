package com.xgame.logic.server.game.soldier.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-PeijianBean - 配件信息
 */
public class PeijianBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int peijianId;
	/***/
	@MsgField(Id = 2)
	public int location;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("peijianId:" + peijianId +",");
		buf.append("location:" + location +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}