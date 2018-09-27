package com.xgame.logic.server.game.armshop.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-CampOutputSucceedBean - 创建建筑物
 */
public class CampOutputSucceedBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/**建筑唯一id*/
	@MsgField(Id = 2)
	public int uid;
	/***/
	@MsgField(Id = 3)
	public int campType;
	/**0快速使用, 1是使用*/
	@MsgField(Id = 4)
	public int num;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierId:" + soldierId +",");
		buf.append("uid:" + uid +",");
		buf.append("campType:" + campType +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}