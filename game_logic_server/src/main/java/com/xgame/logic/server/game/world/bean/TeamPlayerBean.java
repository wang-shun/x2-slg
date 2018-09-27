package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-TeamPlayerBean - 队伍中玩家信息
 */
public class TeamPlayerBean extends XPro {
	/**队伍id*/
	@MsgField(Id = 1)
	public String teamId;
	/**玩家id*/
	@MsgField(Id = 2)
	public long playerId;
	/**玩家名称*/
	@MsgField(Id = 3)
	public String playerName;
	/**玩家头像*/
	@MsgField(Id = 4)
	public String headImg;
	/**士兵数量*/
	@MsgField(Id = 5)
	public int soldierNum;
	/**联盟简称*/
	@MsgField(Id = 6)
	public String allianceAbbr;
	/**到达时间*/
	@MsgField(Id = 7)
	public long remainTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("teamId:" + teamId +",");
		buf.append("playerId:" + playerId +",");
		buf.append("playerName:" + playerName +",");
		buf.append("headImg:" + headImg +",");
		buf.append("soldierNum:" + soldierNum +",");
		buf.append("allianceAbbr:" + allianceAbbr +",");
		buf.append("remainTime:" + remainTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}