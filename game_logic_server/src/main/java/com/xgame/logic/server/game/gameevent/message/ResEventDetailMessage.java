package com.xgame.logic.server.game.gameevent.message;
import com.xgame.logic.server.game.gameevent.bean.EventRankInfoBean;

import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-ResEventDetail - 返回事件详情
 */
public class ResEventDetailMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1009102;
	//模块号
	public static final int FUNCTION_ID=1009;
	//消息号
	public static final int MSG_ID=102;
	
	/**当前排名*/
	@MsgField(Id = 1)
	public EventRankInfoBean currentRank;
	/**前十排名*/
	@MsgField(Id = 2)
	public List<EventRankInfoBean> currentFirstTenRank = new ArrayList<EventRankInfoBean>();
		
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
		buf.append("currentRank:" + currentRank +",");
		buf.append("currentFirstTenRank:{");
		for (int i = 0; i < currentFirstTenRank.size(); i++) {
			buf.append(currentFirstTenRank.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}