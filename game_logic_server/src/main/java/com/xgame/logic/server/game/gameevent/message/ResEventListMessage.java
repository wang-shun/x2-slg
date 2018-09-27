package com.xgame.logic.server.game.gameevent.message;
import com.xgame.logic.server.game.gameevent.bean.EventBean;

import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-ResEventList - 返回事件列表
 */
public class ResEventListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1009101;
	//模块号
	public static final int FUNCTION_ID=1009;
	//消息号
	public static final int MSG_ID=101;
	
	/**事件列表*/
	@MsgField(Id = 1)
	public List<EventBean> eventList = new ArrayList<EventBean>();
		
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
		buf.append("eventList:{");
		for (int i = 0; i < eventList.size(); i++) {
			buf.append(eventList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}