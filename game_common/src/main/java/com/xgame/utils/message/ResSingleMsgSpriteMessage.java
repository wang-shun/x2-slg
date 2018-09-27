package com.xgame.utils.message;

import com.xgame.utils.bean.SingleMsgSpritePro;
import com.xgame.msglib.XMessage;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 消息
 */
public class ResSingleMsgSpriteMessage extends XMessage{
	
	//
	@MsgField(Id = 1)
	public SingleMsgSpritePro singleSpritePro;
	
	
	
	@Override
	public int getId() {
		return 117100;
	}

	@Override
	public String getQueue() {
		return null;
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
		return null;
	}

	@Override
	public HandlerEnum getHandlerEnum() {
		return null;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//
		if(this.singleSpritePro!=null) buf.append("singleSpritePro:" + singleSpritePro.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}