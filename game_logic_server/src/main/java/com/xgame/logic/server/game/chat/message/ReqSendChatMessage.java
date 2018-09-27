package com.xgame.logic.server.game.chat.message;
import com.xgame.msglib.ReqMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ReqSendChat - 发送聊天信息
 */
public class ReqSendChatMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1002201;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=201;
	
	/**频道(1.私聊2.世界3.联盟4.聊天室)*/
	@MsgField(Id = 1)
	public int channel;
	/**发送目标(私聊玩家id，联盟id，聊天室id)*/
	@MsgField(Id = 2)
	public String target;
	/**消息类型(1.普通2。大喇叭3。语音)*/
	@MsgField(Id = 3)
	public int messageType;
	/**内容*/
	@MsgField(Id = 4)
	public String content;
	/**来源*/
	@MsgField(Id = 5)
	public String source;
		
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
		buf.append("channel:" + channel +",");
		buf.append("target:" + target +",");
		buf.append("messageType:" + messageType +",");
		buf.append("content:" + content +",");
		buf.append("source:" + source +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}