package com.xgame.logic.server.game.player.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Player-ReqSimplePlayer - 请求玩家详细信息
 */
public class ReqSimplePlayerMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1003202;
	//模块号
	public static final int FUNCTION_ID=1003;
	//消息号
	public static final int MSG_ID=202;
	
	/**玩家id*/
	@MsgField(Id = 1)
	public List<Long> playerIds = new ArrayList<Long>();
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerIds:{");
		for (int i = 0; i < playerIds.size(); i++) {
			buf.append(playerIds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}