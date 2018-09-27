package com.xgame.logic.server.game.radar.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Radar-ReqInvestigateInfo - 注释
 */
public class ReqInvestigateInfoMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=301301;
	//模块号
	public static final int FUNCTION_ID=301;
	//消息号
	public static final int MSG_ID=301;
	
	/**服务器id*/
	@MsgField(Id = 1)
	public int serverId;
	/***/
	@MsgField(Id = 2)
	public int x;
	/***/
	@MsgField(Id = 3)
	public int y;
		
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
		buf.append("serverId:" + serverId +",");
		buf.append("x:" + x +",");
		buf.append("y:" + y +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}