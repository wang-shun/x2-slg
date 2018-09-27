package com.xgame.logic.server.game.chat.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ChatMessageInfo - 玩家聊天消息
 */
public class ChatMessageInfo extends XPro {
	/**内容*/
	@MsgField(Id = 1)
	public String content;
	/**频道(1.私聊2.世界3.联盟4.聊天室)*/
	@MsgField(Id = 2)
	public int channel;
	/**消息类型(1.普通2。大喇叭3。语音)*/
	@MsgField(Id = 3)
	public int messageType;
	/**目标id*/
	@MsgField(Id = 4)
	public String target;
	/**发送时间*/
	@MsgField(Id = 5)
	public long sendTime;
	/**来源*/
	@MsgField(Id = 6)
	public String source;
	/**发送方详细信息*/
	@MsgField(Id = 7)
	public String senderInfo;
	/**接收方详细信息 私聊用*/
	@MsgField(Id = 8)
	public String targetInfo;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("content:" + content +",");
		buf.append("channel:" + channel +",");
		buf.append("messageType:" + messageType +",");
		buf.append("target:" + target +",");
		buf.append("sendTime:" + sendTime +",");
		buf.append("source:" + source +",");
		buf.append("senderInfo:" + senderInfo +",");
		buf.append("targetInfo:" + targetInfo +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}