package com.xgame.logic.server.game.radar.message;
import com.xgame.logic.server.game.radar.bean.ActivePlayerInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-ResRadar - 所有入侵者的信息
 */
public class ResRadarMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=301101;
	//模块号
	public static final int FUNCTION_ID=301;
	//消息号
	public static final int MSG_ID=101;
	
	/**活跃者信息*/
	@MsgField(Id = 1)
	public List<ActivePlayerInfo> radars = new ArrayList<ActivePlayerInfo>();
		
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
		buf.append("radars:{");
		for (int i = 0; i < radars.size(); i++) {
			buf.append(radars.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}