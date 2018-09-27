package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ReqViewWorldTerritory - 查询领地信息(暂时不需要)
 */
public class ReqViewWorldTerritoryMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=121262;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=262;
	
	/**中心点*/
	@MsgField(Id = 1)
	public Vector2Bean viewCenter;
		
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
		buf.append("viewCenter:" + viewCenter +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}