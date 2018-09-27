package com.xgame.utils;

import com.xgame.msglib.annotation.MsgField;
import com.xgame.msglib.able.MessageReqCallbackable;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.XMessage;
import com.xgame.msglib.able.Communicationable;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 请求销毁士兵消息
 */
public class ReqCampDesitoryMessage extends ReqMessage{
	
	/*唯一id*/
	@MsgField(Id = 1)
	public int soldierId;
	/**/
	@MsgField(Id = 2)
	public int campType;
	/**/
	@MsgField(Id = 3)
	public int num;
	
	
	@Override
	public int getId() {
		return 101300;
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
		//唯一id
		buf.append("soldierId:" + soldierId +",");
		//
		buf.append("campType:" + campType +",");
		//
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}