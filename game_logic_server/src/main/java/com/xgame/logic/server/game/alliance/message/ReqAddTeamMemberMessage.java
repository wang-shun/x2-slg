package com.xgame.logic.server.game.alliance.message;
import com.xgame.msglib.ReqMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ReqAddTeamMember - 添加战队成员
 */
public class ReqAddTeamMemberMessage extends ReqMessage {
	
	//模块号+消息号
	public static final int ID=1007215;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=215;
	
	/**联盟id*/
	@MsgField(Id = 1)
	public long alliaceId;
	/**队伍编号*/
	@MsgField(Id = 2)
	public String teamId;
	/**目标玩家id*/
	@MsgField(Id = 3)
	public long targetPlayerId;
		
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
		return Communicationable.HandlerEnum.CS;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("alliaceId:" + alliaceId +",");
		buf.append("teamId:" + teamId +",");
		buf.append("targetPlayerId:" + targetPlayerId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}