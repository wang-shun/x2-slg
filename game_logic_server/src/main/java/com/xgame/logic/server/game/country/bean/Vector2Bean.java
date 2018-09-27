package com.xgame.logic.server.game.country.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-Vector2Bean - 创建建筑物
 */
public class Vector2Bean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int x;
	/***/
	@MsgField(Id = 2)
	public int y;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("x:" + x +",");
		buf.append("y:" + y +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}