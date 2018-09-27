package com.xgame.logic.server.game.chat.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ReqBuyTyphon - 购买喇叭
 */
public class ReqBuyTyphonMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1002215;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=215;
	
	/**喇叭道具id*/
	@MsgField(Id = 1)
	public int itemId;
	/**数量*/
	@MsgField(Id = 2)
	public int num;
		
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
		buf.append("itemId:" + itemId +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}