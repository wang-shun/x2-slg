package com.xgame.logic.server.game.player.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-MsgEquipmentFragment - 
 */
public class MsgEquipmentFragment extends XPro {
	/***/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public int itemId;
	/***/
	@MsgField(Id = 3)
	public int num;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}