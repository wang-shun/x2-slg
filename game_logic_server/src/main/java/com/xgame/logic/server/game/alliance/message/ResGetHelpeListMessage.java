package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceHelpeBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResGetHelpeList - 返回获取帮助列表
 */
public class ResGetHelpeListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007132;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=132;
	
	/**联盟援建*/
	@MsgField(Id = 1)
	public List<AllianceHelpeBean> allianceHelpe = new ArrayList<AllianceHelpeBean>();
		
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
		buf.append("allianceHelpe:{");
		for (int i = 0; i < allianceHelpe.size(); i++) {
			buf.append(allianceHelpe.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}