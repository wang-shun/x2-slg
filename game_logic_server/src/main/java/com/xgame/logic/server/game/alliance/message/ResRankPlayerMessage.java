package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceRankPlayerBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResRankPlayer - 返回排行数据
 */
public class ResRankPlayerMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007140;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=140;
	
	/**我的排行*/
	@MsgField(Id = 1)
	public int myank;
	/**排行类型*/
	@MsgField(Id = 2)
	public int rankType;
	/**联盟成员排行数据*/
	@MsgField(Id = 3)
	public List<AllianceRankPlayerBean> alliance = new ArrayList<AllianceRankPlayerBean>();
		
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
		buf.append("myank:" + myank +",");
		buf.append("rankType:" + rankType +",");
		buf.append("alliance:{");
		for (int i = 0; i < alliance.size(); i++) {
			buf.append(alliance.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}