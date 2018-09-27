package com.xgame.logic.server.game.chat.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ChatRoomMessageInfo - 房间聊天消息
 */
public class ChatRoomMessageInfo extends XPro {
	/**房间列表*/
	@MsgField(Id = 1)
	public ChatRoomBean chatRoomList;
	/**离线消息列表*/
	@MsgField(Id = 2)
	public List<ChatMessageInfo> chatMessageList = new ArrayList<ChatMessageInfo>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("chatRoomList:" + chatRoomList +",");
		buf.append("chatMessageList:{");
		for (int i = 0; i < chatMessageList.size(); i++) {
			buf.append(chatMessageList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}