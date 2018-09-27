package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-SimpleAllianceBean - 联盟简易信息
 */
public class SimpleAllianceBean extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**联盟等级*/
	@MsgField(Id = 2)
	public int level;
	/**联盟名称*/
	@MsgField(Id = 3)
	public String allianceName;
	/**联盟简写*/
	@MsgField(Id = 4)
	public String abbr;
	/**旗帜*/
	@MsgField(Id = 5)
	public String icon;
	/**战力*/
	@MsgField(Id = 6)
	public long fightPower;
	/**当前人数*/
	@MsgField(Id = 7)
	public int curMember;
	/**最大人数*/
	@MsgField(Id = 8)
	public int maxMember;
	/**盟主名称*/
	@MsgField(Id = 9)
	public String leaderName;
	/**最大人数*/
	@MsgField(Id = 10)
	public String language;
	/**创建时间*/
	@MsgField(Id = 11)
	public long createTime;
	/**自动审批(0否1是)*/
	@MsgField(Id = 12)
	public int auto;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("level:" + level +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("abbr:" + abbr +",");
		buf.append("icon:" + icon +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("curMember:" + curMember +",");
		buf.append("maxMember:" + maxMember +",");
		buf.append("leaderName:" + leaderName +",");
		buf.append("language:" + language +",");
		buf.append("createTime:" + createTime +",");
		buf.append("auto:" + auto +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}