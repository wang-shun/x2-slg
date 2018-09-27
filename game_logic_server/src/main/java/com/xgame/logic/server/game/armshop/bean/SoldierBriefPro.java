package com.xgame.logic.server.game.armshop.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-SoldierBriefPro - 士兵简要信息
 */
public class SoldierBriefPro extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/**数量*/
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