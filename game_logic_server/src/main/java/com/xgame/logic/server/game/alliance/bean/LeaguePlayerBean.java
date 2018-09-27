package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-LeaguePlayerBean - 
 */
public class LeaguePlayerBean extends XPro {
	/**角色ID*/
	@MsgField(Id = 1)
	public long roleId;
	/**角色名称*/
	@MsgField(Id = 2)
	public String roleName;
	/**联盟职位*/
	@MsgField(Id = 3)
	public int leaguePosition;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("roleId:" + roleId +",");
		buf.append("roleName:" + roleName +",");
		buf.append("leaguePosition:" + leaguePosition +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}