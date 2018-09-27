package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-WorldPlayerSignure - 玩家信息签名
 */
public class WorldPlayerSignure extends XPro {
	/**占领或者扎营的玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**占领或者扎营的玩家名称*/
	@MsgField(Id = 2)
	public String playerName;
	/**玩家头像*/
	@MsgField(Id = 3)
	public String playerImg;
	/**联盟id*/
	@MsgField(Id = 4)
	public long allianceId;
	/**联盟名称*/
	@MsgField(Id = 5)
	public String allianceName;
	/**简称*/
	@MsgField(Id = 6)
	public String allianceSimpleName;
	/**联盟头像*/
	@MsgField(Id = 7)
	public String allianceImg;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("playerName:" + playerName +",");
		buf.append("playerImg:" + playerImg +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("allianceSimpleName:" + allianceSimpleName +",");
		buf.append("allianceImg:" + allianceImg +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}