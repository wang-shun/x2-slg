package com.xgame.logic.server.game.vip.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Vip-VipBean - 玩家vip信息
 */
public class VipBean extends XPro {
	/**经验*/
	@MsgField(Id = 1)
	public int exp;
	/**等级*/
	@MsgField(Id = 2)
	public int vipLevel;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("exp:" + exp +",");
		buf.append("vipLevel:" + vipLevel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}