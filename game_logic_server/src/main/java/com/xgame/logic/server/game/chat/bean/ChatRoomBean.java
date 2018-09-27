package com.xgame.logic.server.game.chat.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-ChatRoomBean - 聊天室
 */
public class ChatRoomBean extends XPro {
	/**房间id*/
	@MsgField(Id = 1)
	public long roomId;
	/**房间名称*/
	@MsgField(Id = 2)
	public String name;
	/**描述*/
	@MsgField(Id = 3)
	public String desc;
	/**房间key*/
	@MsgField(Id = 4)
	public String roomKey;
	/**是否公开*/
	@MsgField(Id = 5)
	public boolean password;
	/**服标示*/
	@MsgField(Id = 6)
	public int serverId;
	/**房主id*/
	@MsgField(Id = 7)
	public long roomLeaderId;
	/**房间玩家列表*/
	@MsgField(Id = 8)
	public List<String> roomPlayerList = new ArrayList<String>();
	/**申请加入玩家列表*/
	@MsgField(Id = 9)
	public List<String> applyPlayerIds = new ArrayList<String>();
	/**禁止加入房间列表*/
	@MsgField(Id = 10)
	public List<String> banPlayerIds = new ArrayList<String>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("roomId:" + roomId +",");
		buf.append("name:" + name +",");
		buf.append("desc:" + desc +",");
		buf.append("roomKey:" + roomKey +",");
		buf.append("password:" + password +",");
		buf.append("serverId:" + serverId +",");
		buf.append("roomLeaderId:" + roomLeaderId +",");
		buf.append("roomPlayerList:{");
		for (int i = 0; i < roomPlayerList.size(); i++) {
			buf.append(roomPlayerList.get(i).toString() +",");
		}
		buf.append("applyPlayerIds:{");
		for (int i = 0; i < applyPlayerIds.size(); i++) {
			buf.append(applyPlayerIds.get(i).toString() +",");
		}
		buf.append("banPlayerIds:{");
		for (int i = 0; i < banPlayerIds.size(); i++) {
			buf.append(banPlayerIds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}