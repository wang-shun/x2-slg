package com.xgame.logic.server.game.commander.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-StatisticLongPro - 统计对象long
 */
public class StatisticLongPro extends XPro {
	/**sid*/
	@MsgField(Id = 1)
	public int id;
	/**统计值*/
	@MsgField(Id = 2)
	public long value;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("value:" + value +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}