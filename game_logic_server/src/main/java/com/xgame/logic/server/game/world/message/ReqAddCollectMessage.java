package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ReqAddCollect - 添加收藏信息
 */
public class ReqAddCollectMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=121224;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=224;
	
	/**中心点*/
	@MsgField(Id = 1)
	public Vector2Bean viewCenter;
	/**服务器标识*/
	@MsgField(Id = 2)
	public int serverId;
	/**收藏类型*/
	@MsgField(Id = 3)
	public int type;
		
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
		buf.append("serverId:" + serverId +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}