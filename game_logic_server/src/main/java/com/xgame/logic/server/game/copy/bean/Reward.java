package com.xgame.logic.server.game.copy.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-Reward - 奖励
 */
public class Reward extends XPro {
	/**道具id*/
	@MsgField(Id = 1)
	public int itemId;
	/**数量*/
	@MsgField(Id = 2)
	public int num;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}