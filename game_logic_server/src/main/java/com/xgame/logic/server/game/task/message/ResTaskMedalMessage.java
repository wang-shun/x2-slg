package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.PlayerMedalTaskBean;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResTaskMedal - 推送勋章任务进度
 */
public class ResTaskMedalMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=201107;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=107;
	
	/**玩家活跃度任务信息*/
	@MsgField(Id = 1)
	public PlayerMedalTaskBean playerMedalTaskBean;
		
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
		buf.append("playerMedalTaskBean:" + playerMedalTaskBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}