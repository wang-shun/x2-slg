package com.xgame.logic.server.game.playerattribute.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Playerattribute-AttrbuteProBean - 属性值对象,这里的value是对应的属性值(有可能是基础值 或 百分比值 或 最终值
 */
public class AttrbuteProBean extends XPro {
	/**属性id*/
	@MsgField(Id = 1)
	public int attributeId;
	/**实际的值*/
	@MsgField(Id = 2)
	public double value;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("attributeId:" + attributeId +",");
		buf.append("value:" + value +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}