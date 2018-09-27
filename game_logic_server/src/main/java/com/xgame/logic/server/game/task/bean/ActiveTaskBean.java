package com.xgame.logic.server.game.task.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ActiveTaskBean - 活跃度任务
 */
public class ActiveTaskBean extends XPro {
	/**任务id*/
	@MsgField(Id = 1)
	public int taskId;
	/**当前进度*/
	@MsgField(Id = 2)
	public int currentCount;
	/**最大进度*/
	@MsgField(Id = 3)
	public int maxCount;
	/**已获得活跃度数量*/
	@MsgField(Id = 4)
	public int rewardActiveNum;
	/**最大活跃度数量*/
	@MsgField(Id = 5)
	public int maxActiveNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("taskId:" + taskId +",");
		buf.append("currentCount:" + currentCount +",");
		buf.append("maxCount:" + maxCount +",");
		buf.append("rewardActiveNum:" + rewardActiveNum +",");
		buf.append("maxActiveNum:" + maxActiveNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}