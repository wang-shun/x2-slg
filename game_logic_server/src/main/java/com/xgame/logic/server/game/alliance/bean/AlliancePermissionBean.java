package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AlliancePermissionBean - 联盟权限
 */
public class AlliancePermissionBean extends XPro {
	/**队伍id*/
	@MsgField(Id = 1)
	public int invite;
	/**招募*/
	@MsgField(Id = 2)
	public int recruit;
	/**处理申请*/
	@MsgField(Id = 3)
	public int dealApply;
	/**发送全服邮件*/
	@MsgField(Id = 4)
	public int sendMail;
	/**分配战队队长*/
	@MsgField(Id = 5)
	public int assignTeamLeader;
	/**test*/
	@MsgField(Id = 6)
	public String test;
	/**创建联盟建筑*/
	@MsgField(Id = 7)
	public int createAllianceBuild;
	/**管理联盟建筑*/
	@MsgField(Id = 8)
	public int managerAllianceBuild;
	/**分配联盟奖励*/
	@MsgField(Id = 9)
	public int assignAllianceReward;
	/**管理权限*/
	@MsgField(Id = 10)
	public int managerMemeberLevel;
	/**驱逐成员*/
	@MsgField(Id = 11)
	public int kickmember;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("invite:" + invite +",");
		buf.append("recruit:" + recruit +",");
		buf.append("dealApply:" + dealApply +",");
		buf.append("sendMail:" + sendMail +",");
		buf.append("assignTeamLeader:" + assignTeamLeader +",");
		buf.append("test:" + test +",");
		buf.append("createAllianceBuild:" + createAllianceBuild +",");
		buf.append("managerAllianceBuild:" + managerAllianceBuild +",");
		buf.append("assignAllianceReward:" + assignAllianceReward +",");
		buf.append("managerMemeberLevel:" + managerMemeberLevel +",");
		buf.append("kickmember:" + kickmember +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}