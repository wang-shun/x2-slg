package com.xgame.logic.server.game.world.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-WorldTerritoryBean - 领土信息
 */
public class WorldTerritoryBean extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**简称*/
	@MsgField(Id = 2)
	public String abbr;
	/**等级*/
	@MsgField(Id = 3)
	public int level;
	/**服务器id*/
	@MsgField(Id = 4)
	public int serverId;
	/**坐标信息*/
	@MsgField(Id = 5)
	public Vector2Bean position;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("abbr:" + abbr +",");
		buf.append("level:" + level +",");
		buf.append("serverId:" + serverId +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}