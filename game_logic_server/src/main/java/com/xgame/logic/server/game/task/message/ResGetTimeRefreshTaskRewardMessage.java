package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.PlayerTimeRefreshTaskBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResGetTimeRefreshTaskReward - 领取时间刷新任务奖励返回
 */
public class ResGetTimeRefreshTaskRewardMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201112;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=112;
	
	/**任务id*/
	@MsgField(Id = 1)
	public int taskId;
	/**重置任务(1日常任务2.联盟任务)*/
	@MsgField(Id = 2)
	public int type;
	/**时间刷新任务列表*/
	@MsgField(Id = 3)
	public PlayerTimeRefreshTaskBean playerTimeRefreshTask;
		
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
		buf.append("taskId:" + taskId +",");
		buf.append("type:" + type +",");
		buf.append("playerTimeRefreshTask:" + playerTimeRefreshTask +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}