package com.xgame.logic.server.game.timertask.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * TimerTask-ReqUseSpeedItem - 使用加速道具
 */
public class ReqUseSpeedItemMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=108201;
	//模块号
	public static final int FUNCTION_ID=108;
	//消息号
	public static final int MSG_ID=201;
	
	/***/
	@MsgField(Id = 1)
	public long timerUid;
	/***/
	@MsgField(Id = 2)
	public long itemUid;
	/***/
	@MsgField(Id = 3)
	public int num;
	/***/
	@MsgField(Id = 4)
	public int itemId;
	/**0-通用加速；1-任务加速*/
	@MsgField(Id = 5)
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
		buf.append("timerUid:" + timerUid +",");
		buf.append("itemUid:" + itemUid +",");
		buf.append("num:" + num +",");
		buf.append("itemId:" + itemId +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}