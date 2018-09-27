package com.xgame.logic.server.game.world.message;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResRefusePlayerDefense - 遣返驻军
 */
public class ResRefusePlayerDefenseMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121134;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=134;
	
	/**玩家id*/
	@MsgField(Id = 1)
	public List<Long> playerId = new ArrayList<Long>();
		
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
		buf.append("playerId:{");
		for (int i = 0; i < playerId.size(); i++) {
			buf.append(playerId.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}