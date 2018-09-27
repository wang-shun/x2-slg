package com.xgame.logic.server.game.task.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-TimeTaskInfoBean - 倒计时任务
 */
public class TimeTaskInfoBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public long id;
	/**任务id*/
	@MsgField(Id = 2)
	public int taskId;
	/**任务状态（0未开始1进行中2已完成）*/
	@MsgField(Id = 3)
	public int state;
	/**开始时间*/
	@MsgField(Id = 4)
	public int startTime;
	/**结束时间*/
	@MsgField(Id = 5)
	public int endTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("taskId:" + taskId +",");
		buf.append("state:" + state +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}