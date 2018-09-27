package com.xgame.logic.server.game.task.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-MedalTaskBean - 勋章任务进度
 */
public class MedalTaskBean extends XPro {
	/**勋章任务id*/
	@MsgField(Id = 1)
	public int taskId;
	/**当前进度值（对应几星）*/
	@MsgField(Id = 2)
	public List<Integer> currentValue = new ArrayList<Integer>();
	/**领奖次数*/
	@MsgField(Id = 3)
	public int rewardTimes;
	/**当前任务进度*/
	@MsgField(Id = 4)
	public long progress;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("taskId:" + taskId +",");
		buf.append("currentValue:{");
		for (int i = 0; i < currentValue.size(); i++) {
			buf.append(currentValue.get(i).toString() +",");
		}
		buf.append("rewardTimes:" + rewardTimes +",");
		buf.append("progress:" + progress +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}