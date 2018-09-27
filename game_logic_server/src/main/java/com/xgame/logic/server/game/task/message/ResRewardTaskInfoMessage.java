package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.BaseTaskBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResRewardTaskInfo - 返回领取任务奖励信息
 */
public class ResRewardTaskInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201102;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=102;
	
	/**当前接取基地的任务列表*/
	@MsgField(Id = 1)
	public List<BaseTaskBean> baseTaskList = new ArrayList<BaseTaskBean>();
	/**已完成未领取的任务id*/
	@MsgField(Id = 2)
	public List<Integer> finishTaskId = new ArrayList<Integer>();
	/**领取的任务id奖励*/
	@MsgField(Id = 3)
	public int taskId;
		
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		return "s2s";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public CommandEnum getCommandEnum() {
		return Communicationable.CommandEnum.PLAYERMSG;
	}
	
	@Override
	public HandlerEnum getHandlerEnum() {
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("baseTaskList:{");
		for (int i = 0; i < baseTaskList.size(); i++) {
			buf.append(baseTaskList.get(i).toString() +",");
		}
		buf.append("finishTaskId:{");
		for (int i = 0; i < finishTaskId.size(); i++) {
			buf.append(finishTaskId.get(i).toString() +",");
		}
		buf.append("taskId:" + taskId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}