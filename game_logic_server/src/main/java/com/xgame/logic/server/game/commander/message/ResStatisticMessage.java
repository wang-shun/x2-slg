package com.xgame.logic.server.game.commander.message;
import com.xgame.logic.server.game.commander.bean.StatisticLongPro;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Commander-ResStatistic - 返回指挥官信息
 */
public class ResStatisticMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=130104;
	//模块号
	public static final int FUNCTION_ID=130;
	//消息号
	public static final int MSG_ID=104;
	
	/**指挥官统计信息*/
	@MsgField(Id = 1)
	public List<StatisticLongPro> statisticLongs = new ArrayList<StatisticLongPro>();
		
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
		buf.append("statisticLongs:{");
		for (int i = 0; i < statisticLongs.size(); i++) {
			buf.append(statisticLongs.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}