package com.xgame.logic.server.game.alliance.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceBean - 联盟信息
 */
public class AllianceBean extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**联盟名称*/
	@MsgField(Id = 2)
	public String allianceName;
	/**联盟简写*/
	@MsgField(Id = 3)
	public String abbr;
	/**旗帜*/
	@MsgField(Id = 4)
	public String icon;
	/**修改旗帜时间*/
	@MsgField(Id = 5)
	public long reicon;
	/**联盟简称修改时间*/
	@MsgField(Id = 6)
	public long reabbr;
	/**联盟名称修改时间*/
	@MsgField(Id = 7)
	public long rename;
	/**宣言*/
	@MsgField(Id = 8)
	public String announce;
	/**语言*/
	@MsgField(Id = 9)
	public String language;
	/**国家*/
	@MsgField(Id = 10)
	public String country;
	/**战力*/
	@MsgField(Id = 11)
	public long fightPower;
	/**联盟资金*/
	@MsgField(Id = 12)
	public long cash;
	/**联盟等级*/
	@MsgField(Id = 13)
	public int level;
	/**自动审批(0否1是)*/
	@MsgField(Id = 14)
	public int auto;
	/**盟主id*/
	@MsgField(Id = 15)
	public long leaderId;
	/**盟主名称*/
	@MsgField(Id = 16)
	public String leaderName;
	/**当前人数*/
	@MsgField(Id = 17)
	public int curMember;
	/**最大人数*/
	@MsgField(Id = 18)
	public int maxMember;
	/**创建时间*/
	@MsgField(Id = 19)
	public long createTime;
	/**公告结束时间*/
	@MsgField(Id = 20)
	public long noticeEndTime;
	/**联盟头衔*/
	@MsgField(Id = 21)
	public AllianceTitleBean allianceTitleBean;
	/**联盟官员列表*/
	@MsgField(Id = 22)
	public List<AllianceOfficeBean> allianceOfficeList = new ArrayList<AllianceOfficeBean>();
	/**联盟战队列表*/
	@MsgField(Id = 23)
	public List<AllianceTeamBean> allianceTeamList = new ArrayList<AllianceTeamBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("abbr:" + abbr +",");
		buf.append("icon:" + icon +",");
		buf.append("reicon:" + reicon +",");
		buf.append("reabbr:" + reabbr +",");
		buf.append("rename:" + rename +",");
		buf.append("announce:" + announce +",");
		buf.append("language:" + language +",");
		buf.append("country:" + country +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("cash:" + cash +",");
		buf.append("level:" + level +",");
		buf.append("auto:" + auto +",");
		buf.append("leaderId:" + leaderId +",");
		buf.append("leaderName:" + leaderName +",");
		buf.append("curMember:" + curMember +",");
		buf.append("maxMember:" + maxMember +",");
		buf.append("createTime:" + createTime +",");
		buf.append("noticeEndTime:" + noticeEndTime +",");
		buf.append("allianceTitleBean:" + allianceTitleBean +",");
		buf.append("allianceOfficeList:{");
		for (int i = 0; i < allianceOfficeList.size(); i++) {
			buf.append(allianceOfficeList.get(i).toString() +",");
		}
		buf.append("allianceTeamList:{");
		for (int i = 0; i < allianceTeamList.size(); i++) {
			buf.append(allianceTeamList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}