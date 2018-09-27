package com.xgame.logic.server.game.country.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-TransformBean - 创建建筑物
 */
public class TransformBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int uid;
	/***/
	@MsgField(Id = 2)
	public Vector2Bean vector2Bean;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("vector2Bean:" + vector2Bean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}