package com.xgame.logic.server.game.duplicate.message;

import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 领取宝箱消息
 */
public class ReqRewardBoxMessage extends ReqMessage{
	
	public static final int ID=2017203;
	
	public static final int FUNCTION_ID=2017;
	
	public static final int MSG_ID=203;
	
	/*领取宝箱（1-刻度5, 2-刻度10,3-刻度15）*/
	@MsgField(Id = 1)
	public int index;
	
	
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
		//领取宝箱（1-刻度5, 2-刻度10,3-刻度15）
		buf.append("index:" + index +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}