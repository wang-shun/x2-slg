package com.xgame.logic.server.game.alliance.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AlliancePlayerViewBean - 联盟玩家展示数据
 */
public class AlliancePlayerViewBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**玩家名称*/
	@MsgField(Id = 2)
	public String name;
	/**玩家头像*/
	@MsgField(Id = 3)
	public String img;
	/**战力*/
	@MsgField(Id = 4)
	public long fightPower;
	/**领地数量*/
	@MsgField(Id = 5)
	public int territoryNum;
	/**坐标*/
	@MsgField(Id = 6)
	public Vector2Bean vector2Bean;
	/**离线时间*/
	@MsgField(Id = 7)
	public long offlineTime;
	/**是否在线*/
	@MsgField(Id = 8)
	public boolean online;
	/**队伍id*/
	@MsgField(Id = 9)
	public String teamId;
	/**成员权限*/
	@MsgField(Id = 10)
	public AlliancePermissionBean alliancePermissionBean;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("name:" + name +",");
		buf.append("img:" + img +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("territoryNum:" + territoryNum +",");
		buf.append("vector2Bean:" + vector2Bean +",");
		buf.append("offlineTime:" + offlineTime +",");
		buf.append("online:" + online +",");
		buf.append("teamId:" + teamId +",");
		buf.append("alliancePermissionBean:" + alliancePermissionBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}