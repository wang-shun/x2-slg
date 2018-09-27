package com.xgame.logic.server.game.world.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-WorldSimpleSoldierBean - 精灵信息
 */
public class WorldSimpleSoldierBean extends XPro {
	/**序列*/
	@MsgField(Id = 1)
	public int index;
	/**坐标*/
	@MsgField(Id = 2)
	public Vector2Bean position;
	/**兵*/
	@MsgField(Id = 3)
	public SoldierBean soldiers;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("index:" + index +",");
		buf.append("position:" + position +",");
		buf.append("soldiers:" + soldiers +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}