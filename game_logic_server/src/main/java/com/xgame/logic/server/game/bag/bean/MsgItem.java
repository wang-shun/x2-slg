package com.xgame.logic.server.game.bag.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Bag-MsgItem - 
 */
public class MsgItem extends XPro {
	/***/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public int itemId;
	/***/
	@MsgField(Id = 3)
	public int num;
	/***/
	@MsgField(Id = 4)
	public long startTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		buf.append("startTime:" + startTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}