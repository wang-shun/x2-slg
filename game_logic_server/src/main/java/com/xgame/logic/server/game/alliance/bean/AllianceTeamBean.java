package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceTeamBean - 联盟战队
 */
public class AllianceTeamBean extends XPro {
	/**队伍id*/
	@MsgField(Id = 1)
	public String teamId;
	/**联盟id*/
	@MsgField(Id = 2)
	public long allianceId;
	/**战队队长*/
	@MsgField(Id = 3)
	public AlliancePlayerViewBean teamLeader;
	/**玩家id*/
	@MsgField(Id = 4)
	public String icon;
	/**战队名称*/
	@MsgField(Id = 5)
	public String teamName;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("teamId:" + teamId +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("teamLeader:" + teamLeader +",");
		buf.append("icon:" + icon +",");
		buf.append("teamName:" + teamName +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}