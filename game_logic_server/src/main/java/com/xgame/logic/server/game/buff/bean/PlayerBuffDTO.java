package com.xgame.logic.server.game.buff.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Buff-PlayerBuffDTO - 玩家buff
 */
public class PlayerBuffDTO extends XPro {
	/**buff唯一id*/
	@MsgField(Id = 1)
	public String buffId;
	/**buff类型*/
	@MsgField(Id = 2)
	public int buffType;
	/**buffkey*/
	@MsgField(Id = 3)
	public String buffKey;
	/**开始时间*/
	@MsgField(Id = 4)
	public long startTime;
	/**结束时间*/
	@MsgField(Id = 5)
	public long endTime;
	/**任务id*/
	@MsgField(Id = 6)
	public long taskId;
	/**buff携带系数*/
	@MsgField(Id = 7)
	public String addition;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("buffId:" + buffId +",");
		buf.append("buffType:" + buffType +",");
		buf.append("buffKey:" + buffKey +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("taskId:" + taskId +",");
		buf.append("addition:" + addition +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}