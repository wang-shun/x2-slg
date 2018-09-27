package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-DefenseTowerPro - 防御塔pro
 */
public class DefenseTowerPro extends XPro {
	/**防御塔数量*/
	@MsgField(Id = 1)
	public int defnseId;
	/**防御塔等级*/
	@MsgField(Id = 2)
	public int defnseLevel;
	/**防御塔位置*/
	@MsgField(Id = 3)
	public Vector2Bean loaction;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("defnseId:" + defnseId +",");
		buf.append("defnseLevel:" + defnseLevel +",");
		buf.append("loaction:" + loaction +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}