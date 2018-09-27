package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 联盟权限
 * @author jacky.jiang
 *
 */
public class AlliancePermission implements JBaseTransform {
	
	/**
	 * 邀请
	 */
	private boolean invite;
	
	/**
	 * 发布招募信息
	 */
	private boolean recruit;
	
	/**
	 * 处理申请
	 */
	private boolean dealApply;
	
	/**
	 * 全服邮件
	 */
	private boolean sendMail;
	
	/**
	 * 分配战队队长
	 */
	private boolean assignTeamLeader;
	
	/**
	 * 创建联盟建筑
	 */
	private boolean createAllianceBuild;
	
	/**
	 * 管理联盟建筑
	 */
	private boolean managerAllianceBuild;
	
	/**
	 * 分配联盟奖励
	 */
	private boolean assignAllianceReward;
	
	/**
	 * 管理成员阶级
	 */
	private boolean managerMemeberLevel;
	
	/**
	 * 驱逐成员
	 */
	private boolean kickmember;

	public boolean isInvite() {
		return invite;
	}

	public void setInvite(boolean invite) {
		this.invite = invite;
	}

	public boolean isRecruit() {
		return recruit;
	}

	public void setRecruit(boolean recruit) {
		this.recruit = recruit;
	}

	public boolean isDealApply() {
		return dealApply;
	}

	public void setDealApply(boolean dealApply) {
		this.dealApply = dealApply;
	}

	public boolean isSendMail() {
		return sendMail;
	}

	public void setSendMail(boolean sendMail) {
		this.sendMail = sendMail;
	}

	public boolean isAssignTeamLeader() {
		return assignTeamLeader;
	}

	public void setAssignTeamLeader(boolean assignTeamLeader) {
		this.assignTeamLeader = assignTeamLeader;
	}

	public boolean isCreateAllianceBuild() {
		return createAllianceBuild;
	}

	public void setCreateAllianceBuild(boolean createAllianceBuild) {
		this.createAllianceBuild = createAllianceBuild;
	}

	public boolean isManagerAllianceBuild() {
		return managerAllianceBuild;
	}

	public void setManagerAllianceBuild(boolean managerAllianceBuild) {
		this.managerAllianceBuild = managerAllianceBuild;
	}

	public boolean isAssignAllianceReward() {
		return assignAllianceReward;
	}

	public void setAssignAllianceReward(boolean assignAllianceReward) {
		this.assignAllianceReward = assignAllianceReward;
	}

	public boolean isManagerMemeberLevel() {
		return managerMemeberLevel;
	}

	public void setManagerMemeberLevel(boolean managerMemeberLevel) {
		this.managerMemeberLevel = managerMemeberLevel;
	}

	public boolean isKickmember() {
		return kickmember;
	}

	public void setKickmember(boolean kickmember) {
		this.kickmember = kickmember;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("invite", invite);
		jbaseData.put("recruit", recruit);
		jbaseData.put("dealApply", dealApply);
		jbaseData.put("sendMail", sendMail);
		jbaseData.put("assignTeamLeader", assignTeamLeader);
		jbaseData.put("createAllianceBuild", createAllianceBuild);
		jbaseData.put("managerAllianceBuild", managerAllianceBuild);
		jbaseData.put("assignAllianceReward", assignAllianceReward);
		jbaseData.put("managerMemeberLevel", managerMemeberLevel);
		jbaseData.put("kickmember", kickmember);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.invite = jBaseData.getBoolean("invite", false);
		this.recruit = jBaseData.getBoolean("recruit", false);
		this.dealApply = jBaseData.getBoolean("dealApply", false);
		this.sendMail = jBaseData.getBoolean("sendMail", false);
		this.assignTeamLeader = jBaseData.getBoolean("assignTeamLeader", false);
		this.createAllianceBuild = jBaseData.getBoolean("createAllianceBuild",false);
		this.managerAllianceBuild = jBaseData.getBoolean("managerAllianceBuild", false);
		this.assignAllianceReward = jBaseData.getBoolean("assignAllianceReward", false);
		this.managerMemeberLevel = jBaseData.getBoolean("managerMemeberLevel",false);
		this.kickmember = jBaseData.getBoolean("kickmember", false);
	}
}
