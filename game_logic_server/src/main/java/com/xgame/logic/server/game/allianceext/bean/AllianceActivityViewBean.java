package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceActivityViewBean - 联盟活跃度排行信息
 */
public class AllianceActivityViewBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**玩家名称*/
	@MsgField(Id = 2)
	public String playerName;
	/**活跃度积分*/
	@MsgField(Id = 3)
	public int activityScore;
	/**头像*/
	@MsgField(Id = 4)
	public String headImg;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("playerName:" + playerName +",");
		buf.append("activityScore:" + activityScore +",");
		buf.append("headImg:" + headImg +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}