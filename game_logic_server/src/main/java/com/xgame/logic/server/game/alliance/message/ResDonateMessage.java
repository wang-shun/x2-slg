package com.xgame.logic.server.game.alliance.message;
import com.xgame.logic.server.game.alliance.bean.AllianceBean;
import com.xgame.logic.server.game.alliance.bean.AllianceMemberBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResDonate - 返回捐献成功（推送联盟变更消息）
 */
public class ResDonateMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007112;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=112;
	
	/**返回联盟数据*/
	@MsgField(Id = 1)
	public AllianceBean alliance;
	/**联盟成员信息*/
	@MsgField(Id = 2)
	public AllianceMemberBean allianceMember;
		
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
		buf.append("alliance:" + alliance +",");
		buf.append("allianceMember:" + allianceMember +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}