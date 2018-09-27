package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceMemberBean - 玩家联盟信息
 */
public class AllianceMemberBean extends XPro {
	/**权限(1-5 R1-R5)*/
	@MsgField(Id = 1)
	public int allianceRank;
	/**联盟贡献*/
	@MsgField(Id = 2)
	public int donate;
	/**历史贡献*/
	@MsgField(Id = 3)
	public int historyDonate;
	/**今日贡献值*/
	@MsgField(Id = 4)
	public int dayDonate;
	/**援建贡献值*/
	@MsgField(Id = 5)
	public int helpDonate;
	/**队伍id*/
	@MsgField(Id = 6)
	public String teamId;
	/**加入联盟时间*/
	@MsgField(Id = 7)
	public long joinTime;
	/**石油捐献次数*/
	@MsgField(Id = 8)
	public int oilCount;
	/**稀土捐献次数*/
	@MsgField(Id = 9)
	public int rareCount;
	/**钢材捐献次数*/
	@MsgField(Id = 10)
	public int steelCount;
	/**黄金捐献次数*/
	@MsgField(Id = 11)
	public int moneyCount;
	/**钻石捐献次数*/
	@MsgField(Id = 12)
	public int diamondCount;
	/**战力*/
	@MsgField(Id = 13)
	public long fightPower;
	/**联盟玩家权限*/
	@MsgField(Id = 14)
	public AlliancePermissionBean alliancePermissionBean;
	/**联盟申请标记时间*/
	@MsgField(Id = 15)
	public long applyTagTime;
	/**刷新时间*/
	@MsgField(Id = 16)
	public long refreshTime;
	/**刷新时间*/
	@MsgField(Id = 17)
	public long playerId;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceRank:" + allianceRank +",");
		buf.append("donate:" + donate +",");
		buf.append("historyDonate:" + historyDonate +",");
		buf.append("dayDonate:" + dayDonate +",");
		buf.append("helpDonate:" + helpDonate +",");
		buf.append("teamId:" + teamId +",");
		buf.append("joinTime:" + joinTime +",");
		buf.append("oilCount:" + oilCount +",");
		buf.append("rareCount:" + rareCount +",");
		buf.append("steelCount:" + steelCount +",");
		buf.append("moneyCount:" + moneyCount +",");
		buf.append("diamondCount:" + diamondCount +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("alliancePermissionBean:" + alliancePermissionBean +",");
		buf.append("applyTagTime:" + applyTagTime +",");
		buf.append("refreshTime:" + refreshTime +",");
		buf.append("playerId:" + playerId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}