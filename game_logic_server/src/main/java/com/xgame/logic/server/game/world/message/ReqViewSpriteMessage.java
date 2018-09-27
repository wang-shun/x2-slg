package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ReqViewSprite - 请求周边消息
 */
public class ReqViewSpriteMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=121201;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=201;
	
	/**显示中心*/
	@MsgField(Id = 1)
	public Vector2Bean viewCenter;
	/**是否显示空地所属信息*/
	@MsgField(Id = 2)
	public boolean terrian;
	/**所在服务器id*/
	@MsgField(Id = 3)
	public int serverId;
		
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
		buf.append("terrian:" + terrian +",");
		buf.append("serverId:" + serverId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}