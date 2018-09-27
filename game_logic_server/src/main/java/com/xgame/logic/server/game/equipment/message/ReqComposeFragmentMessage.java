package com.xgame.logic.server.game.equipment.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Equipment-ReqComposeFragment - 请求合成材料
 */
public class ReqComposeFragmentMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=300208;
	//模块号
	public static final int FUNCTION_ID=300;
	//消息号
	public static final int MSG_ID=208;
	
	/**材料唯一ID*/
	@MsgField(Id = 1)
	public long id;
	/***/
	@MsgField(Id = 2)
	public Integer fragmentID;
	/***/
	@MsgField(Id = 3)
	public Integer num;
		
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
		buf.append("id:" + id +",");
		buf.append("fragmentID:" + fragmentID +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}