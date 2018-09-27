package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceOfficeBean;
import com.xgame.logic.server.game.alliance.bean.AllianceTeamBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResAllianceOffice - 返回联盟官员信息
 */
public class ResAllianceOfficeMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007113;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=113;
	
	/**联盟官员列表*/
	@MsgField(Id = 1)
	public List<AllianceOfficeBean> allianceOfficeList = new ArrayList<AllianceOfficeBean>();
	/**联盟战队列表*/
	@MsgField(Id = 2)
	public List<AllianceTeamBean> allianceTeamList = new ArrayList<AllianceTeamBean>();
		
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
		buf.append("allianceOfficeList:{");
		for (int i = 0; i < allianceOfficeList.size(); i++) {
			buf.append(allianceOfficeList.get(i).toString() +",");
		}
		buf.append("allianceTeamList:{");
		for (int i = 0; i < allianceTeamList.size(); i++) {
			buf.append(allianceTeamList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}