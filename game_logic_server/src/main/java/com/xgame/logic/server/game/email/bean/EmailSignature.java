package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-EmailSignature - 接收发送签名
 */
public class EmailSignature extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**玩家名字*/
	@MsgField(Id = 2)
	public String playerName;
	/**联盟ID*/
	@MsgField(Id = 3)
	public long gangId;
	/**联盟简称*/
	@MsgField(Id = 4)
	public String allianceAbbr;
	/**联盟名*/
	@MsgField(Id = 5)
	public String allianceName;
	/**头像ID*/
	@MsgField(Id = 6)
	public String headId;
	/**服务器ID*/
	@MsgField(Id = 7)
	public int serverId;
	/**地点坐标*/
	@MsgField(Id = 8)
	public Vector2Bean position;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("playerName:" + playerName +",");
		buf.append("gangId:" + gangId +",");
		buf.append("allianceAbbr:" + allianceAbbr +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("headId:" + headId +",");
		buf.append("serverId:" + serverId +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}