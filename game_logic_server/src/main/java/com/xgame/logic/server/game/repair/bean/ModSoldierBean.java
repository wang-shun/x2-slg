package com.xgame.logic.server.game.repair.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Repair-ModSoldierBean - 创建建筑物
 */
public class ModSoldierBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/***/
	@MsgField(Id = 2)
	public int num;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierId:" + soldierId +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}