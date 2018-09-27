package com.xgame.logic.server.game.gameevent.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-EventRankInfoBean - 排行信息
 */
public class EventRankInfoBean extends XPro {
	/**排行*/
	@MsgField(Id = 1)
	public int rank;
	/**名称*/
	@MsgField(Id = 2)
	public String name;
	/**积分*/
	@MsgField(Id = 3)
	public long score;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("rank:" + rank +",");
		buf.append("name:" + name +",");
		buf.append("score:" + score +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}