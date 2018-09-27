package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-MarchInfo - 超级矿玩家采集信息
 */
public class MarchInfo extends XPro {
	/**行军队列ID*/
	@MsgField(Id = 1)
	public long marchId;
	/**玩家ID*/
	@MsgField(Id = 2)
	public long playerId;
	/**玩家头像*/
	@MsgField(Id = 3)
	public String icon;
	/**联盟简称*/
	@MsgField(Id = 4)
	public String abbr;
	/**已采集数量*/
	@MsgField(Id = 5)
	public int explorerNum;
	/**总采集负载数量*/
	@MsgField(Id = 6)
	public int totalWeight;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("marchId:" + marchId +",");
		buf.append("playerId:" + playerId +",");
		buf.append("icon:" + icon +",");
		buf.append("abbr:" + abbr +",");
		buf.append("explorerNum:" + explorerNum +",");
		buf.append("totalWeight:" + totalWeight +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}