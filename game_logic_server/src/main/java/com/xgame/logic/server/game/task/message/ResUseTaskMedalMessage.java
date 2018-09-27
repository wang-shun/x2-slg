package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.PlayerMedalTaskBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResUseTaskMedal - 返回使用任务勋章
 */
public class ResUseTaskMedalMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201106;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=106;
	
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
		return Communicationable.HandlerEnum.SC;
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