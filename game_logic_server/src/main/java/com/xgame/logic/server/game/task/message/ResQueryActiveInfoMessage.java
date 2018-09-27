package com.xgame.logic.server.game.task.message;
import com.xgame.logic.server.game.task.bean.PlayerActiveTaskBean;
import com.xgame.logic.server.game.task.bean.ActiveTaskBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-ResQueryActiveInfo - 返回活跃度
 */
public class ResQueryActiveInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=201103;
	//模块号
	public static final int FUNCTION_ID=201;
	//消息号
	public static final int MSG_ID=103;
	
	/**玩家活跃度任务信息*/
	@MsgField(Id = 1)
	public PlayerActiveTaskBean playerActiveTask;
	/**活跃度任务列表*/
	@MsgField(Id = 2)
	public List<ActiveTaskBean> activeTaskList = new ArrayList<ActiveTaskBean>();
		
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
		buf.append("playerActiveTask:" + playerActiveTask +",");
		buf.append("activeTaskList:{");
		for (int i = 0; i < activeTaskList.size(); i++) {
			buf.append(activeTaskList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}