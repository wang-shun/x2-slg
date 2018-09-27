package com.xgame.logic.server.game.armshop.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-ArmsFactoryOwnBean - 创建建筑物
 */
public class ArmsFactoryOwnBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/***/
	@MsgField(Id = 2)
	public int number;
	/***/
	@MsgField(Id = 3)
	public int collectNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierId:" + soldierId +",");
		buf.append("number:" + number +",");
		buf.append("collectNum:" + collectNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}