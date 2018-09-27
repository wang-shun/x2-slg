package com.xgame.logic.server.game.playerattribute.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-AttrbuteAppenderBean - 属性来源对象
 */
public class AttrbuteAppenderBean extends XPro {
	/**加成者ID:0=建筑 1=指挥官天赋 2=植入体 3=科技 4=道具 5=雷达 6=联盟科技 7=VIP*/
	@MsgField(Id = 1)
	public int appenderId;
	/**属性值*/
	@MsgField(Id = 2)
	public double value;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("appenderId:" + appenderId +",");
		buf.append("value:" + value +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}