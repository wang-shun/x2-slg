package com.xgame.logic.server.game.task.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-BaseTaskBean - 基地任务
 */
public class BaseTaskBean extends XPro {
	/**任务id*/
	@MsgField(Id = 1)
	public int taskId;
	/**任务进度*/
	@MsgField(Id = 2)
	public long progress;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("taskId:" + taskId +",");
		buf.append("progress:" + progress +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}