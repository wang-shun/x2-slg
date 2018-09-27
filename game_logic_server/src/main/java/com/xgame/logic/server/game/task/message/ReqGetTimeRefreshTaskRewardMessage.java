package com.xgame.logic.server.game.task.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ReqGetTimeRefreshTaskReward - 领取时间刷新任务奖励
 */
public class ReqGetTimeRefreshTaskRewardMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=201212;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=212;
	
	/**重置任务(1日常任务2.联盟任务)*/
	@MsgField(Id = 1)
	public int type;
	/**唯一id*/
	@MsgField(Id = 2)
	public long id;
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("type:" + type +",");
		buf.append("id:" + id +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}