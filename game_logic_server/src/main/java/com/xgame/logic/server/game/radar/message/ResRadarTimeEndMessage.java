package com.xgame.logic.server.game.radar.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-ResRadarTimeEnd - 雷达攻击者到时间删掉
 */
public class ResRadarTimeEndMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=301102;
	//模块号
	public static final int FUNCTION_ID=301;
	//消息号
	public static final int MSG_ID=102;
	
	/**到时的id*/
	@MsgField(Id = 1)
	public List<Long> marchIds = new ArrayList<Long>();
		
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
		buf.append("marchIds:{");
		for (int i = 0; i < marchIds.size(); i++) {
			buf.append(marchIds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}