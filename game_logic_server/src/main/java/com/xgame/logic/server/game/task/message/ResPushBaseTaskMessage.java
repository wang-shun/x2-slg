package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.BaseTaskBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResPushBaseTask - 推送基地任务更新
 */
public class ResPushBaseTaskMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201113;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=113;
	
	/**基地任务*/
	@MsgField(Id = 1)
	public BaseTaskBean baseTaskBean;
		
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
		buf.append("baseTaskBean:" + baseTaskBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}