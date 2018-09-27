package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.AllianceScienceBean;
import com.xgame.logic.server.game.allianceext.bean.PlayerAllianceExtBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResAllianceScienceDonate - 返回科技捐赠（推送科技变更消息）
 */
public class ResAllianceScienceDonateMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210106;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=106;
	
	/**联盟科技*/
	@MsgField(Id = 1)
	public AllianceScienceBean allianceScienceBean;
	/**联盟成员扩展信息*/
	@MsgField(Id = 2)
	public PlayerAllianceExtBean playerAllianceExtBean;
		
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
		buf.append("allianceScienceBean:" + allianceScienceBean +",");
		buf.append("playerAllianceExtBean:" + playerAllianceExtBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}