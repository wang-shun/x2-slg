package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.ActiveTaskBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResPushActiveTask - 推送活跃度任务更新
 */
public class ResPushActiveTaskMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201114;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=114;
	
	/**活跃度任务*/
	@MsgField(Id = 1)
	public ActiveTaskBean activeTaskBean;
	/**已获得活跃度数量*/
	@MsgField(Id = 2)
	public int rewardActiveNum;
		
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
		buf.append("activeTaskBean:" + activeTaskBean +",");
		buf.append("rewardActiveNum:" + rewardActiveNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}