package com.xgame.logic.server.game.chat.message;
import com.xgame.logic.server.game.chat.bean.ChatRoomMessageInfo;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ResApplyAddRoom - 返回申请信息(发给申请人)
 */
public class ResApplyAddRoomMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1002103;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=103;
	
	/**0申请中1成功 */
	@MsgField(Id = 1)
	public int success;
	/**房间聊天信息*/
	@MsgField(Id = 2)
	public ChatRoomMessageInfo chatRoomMessageInfo;
		
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
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("success:" + success +",");
		buf.append("chatRoomMessageInfo:" + chatRoomMessageInfo +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}