package com.xgame.logic.server.game.chat.message;
import com.xgame.logic.server.game.chat.bean.ChatRoomBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ResRoomChange - 房间变更信息
 */
public class ResRoomChangeMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1002102;
	//模块号
	public static final int FUNCTION_ID=1002;
	//消息号
	public static final int MSG_ID=102;
	
	/**房间变更类型(0创建1自动加入2加入申请3处理申请通过4处理申请拒绝5退出6解散7踢出8编辑9添加禁入10移除禁入)*/
	@MsgField(Id = 1)
	public int type;
	/**房间变更信息*/
	@MsgField(Id = 2)
	public ChatRoomBean chatRoom;
		
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
		buf.append("type:" + type +",");
		buf.append("chatRoom:" + chatRoom +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}