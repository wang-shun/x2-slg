package com.xgame.logic.server.game.timertask.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * TimerTask-TimerBean - 时间队列消息
 */
public class TimerBean extends XPro {
	/**队列id*/
	@MsgField(Id = 1)
	public long timerUid;
	/**开始时间*/
	@MsgField(Id = 2)
	public int startTime;
	/**结束时间*/
	@MsgField(Id = 3)
	public int endTime;
	/**队列类型*/
	@MsgField(Id = 4)
	public int type;
	/**队列参数*/
	@MsgField(Id = 5)
	public String data;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("timerUid:" + timerUid +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("type:" + type +",");
		buf.append("data:" + data +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}