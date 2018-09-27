package com.xgame.logic.server.game.gameevent.message;
import com.xgame.logic.server.game.gameevent.bean.EventRankInfoBeanList;

import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-ResHistoryRank - 返回历史排名列表
 */
public class ResHistoryRankMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1009103;
	//模块号
	public static final int FUNCTION_ID=1009;
	//消息号
	public static final int MSG_ID=103;
	
	/**历史排名*/
	@MsgField(Id = 1)
	public List<EventRankInfoBeanList> historyRankList = new ArrayList<EventRankInfoBeanList>();
		
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
		buf.append("historyRankList:{");
		for (int i = 0; i < historyRankList.size(); i++) {
			buf.append(historyRankList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}