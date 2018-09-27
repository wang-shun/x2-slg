package com.xgame.logic.server.game.gameevent.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-EventBean - 事件信息
 */
public class EventBean extends XPro {
	/**id*/
	@MsgField(Id = 1)
	public long sid;
	/**事件id*/
	@MsgField(Id = 2)
	public int eventId;
	/**开始时间*/
	@MsgField(Id = 3)
	public long startTime;
	/**结束时间*/
	@MsgField(Id = 4)
	public long endTime;
	/**青铜积分*/
	@MsgField(Id = 5)
	public int score1;
	/**白银积分*/
	@MsgField(Id = 6)
	public int score2;
	/**黄金积分*/
	@MsgField(Id = 7)
	public int score3;
	/**青铜宝箱*/
	@MsgField(Id = 8)
	public String rewards1;
	/**白银宝箱*/
	@MsgField(Id = 9)
	public String rewards2;
	/**黄金宝箱*/
	@MsgField(Id = 10)
	public String rewards3;
	/**青铜宝箱状态(0未发放,1已发放)*/
	@MsgField(Id = 11)
	public int statu1;
	/**白银宝箱状态(0未发放,1已发放)*/
	@MsgField(Id = 12)
	public int statu2;
	/**黄金宝箱状态(0未发放,1已发放)*/
	@MsgField(Id = 13)
	public int statu3;
	/**当前事件积分*/
	@MsgField(Id = 14)
	public int currentScore;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("sid:" + sid +",");
		buf.append("eventId:" + eventId +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("score1:" + score1 +",");
		buf.append("score2:" + score2 +",");
		buf.append("score3:" + score3 +",");
		buf.append("rewards1:" + rewards1 +",");
		buf.append("rewards2:" + rewards2 +",");
		buf.append("rewards3:" + rewards3 +",");
		buf.append("statu1:" + statu1 +",");
		buf.append("statu2:" + statu2 +",");
		buf.append("statu3:" + statu3 +",");
		buf.append("currentScore:" + currentScore +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}