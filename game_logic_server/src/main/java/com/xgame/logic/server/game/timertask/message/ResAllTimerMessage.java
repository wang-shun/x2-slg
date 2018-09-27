package com.xgame.logic.server.game.timertask.message;
import com.xgame.logic.server.game.timertask.bean.TimerBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * TimerTask-ResAllTimer - 返回所有的队列
 */
public class ResAllTimerMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=108100;
	//模块号
	public static final int FUNCTION_ID=108;
	//消息号
	public static final int MSG_ID=100;
	
	/***/
	@MsgField(Id = 1)
	public List<TimerBean> timers = new ArrayList<TimerBean>();
		
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
		buf.append("timers:{");
		for (int i = 0; i < timers.size(); i++) {
			buf.append(timers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}