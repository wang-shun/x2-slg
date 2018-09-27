package com.xgame.logic.server.game.cross.entity;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.player.entity.RoleInfo;

import io.protostuff.Tag;


/**
 * 玩家简易信息
 * @author jacky.jiang
 *
 */
public class SimpleRoleInfo implements JBaseTransform {
	
	/**
	 * 玩家id
	 */
	@Tag(1)
	private long playerId;
	
	/**
	 * 用户名
	 */
	@Tag(2)
	private String userName;
	
	/**
	 * 服标识
	 */
	@Tag(3)
	private int serverId;
	
	/**
	 * 玩家名称
	 */
	@Tag(4)
	private String name;
	
	/**
	 * 头像
	 */
	@Tag(5)
	private String img;
	
	/**
	 * 军团id
	 */
	@Tag(6)
	private long allianceId;
	
	/**
	 * 军团名称
	 */
	@Tag(7)
	private String allianceName;
	
	/**
	 * vip等级
	 */
	@Tag(8)
	private int vip;
	
	/**
	 * 玩家等级
	 */
	@Tag(9)
	private int level;
	
	/**
	 * 军衔等级
	 */
	@Tag(10)
	private int militaryLevel;
	
	/**
	 * 时间戳
	 */
	@Tag(11)
	private long updateTime;
	
	/**
	 * 行政大楼等级
	 */
	@Tag(12)
	private int buildLevel;
	
	/**
	 * 军团简称
	 */
	@Tag(13)
	private String allianceAbbr;
	
	/**
	 * 军团头衔
	 */
	@Tag(14)
	private String allianceTitle;
	
	/**
	 * 军团头衔名称
	 */
	@Tag(15)
	private String allianceTitleName;
	
	/**
	 * 世界政府头衔
	 */
	@Tag(16)
	private String governmentTitle;
	
	/**
	 * 玩家
	 * @param roleInfo
	 * @return
	 */
	public boolean compareRoleInfo(RoleInfo roleInfo, long allianceId ,String allianceName ,String allianceAbbr,String allianceTitle,String allianceTitleName,String governmentTitle) {
		
		boolean flag = false;
		if(playerId != roleInfo.getBasics().getRoleId()) {
			return false;
		}
		
		if(!StringUtils.equals(userName, roleInfo.getBasics().getUserName())) {
			flag = true;
		}
		
		if(serverId != roleInfo.getBasics().getServerArea()) {
			flag = true;
		}
		
		if(!StringUtils.equals(name, roleInfo.getBasics().getRoleName())) {
			flag = true;
		}
		
		if(!StringUtils.equals(img, roleInfo.getBasics().getHeadImg())) {
			flag = true;
		}
		
		if(this.allianceId != allianceId) {
			flag = true;
		}
		
		if(!StringUtils.equals(this.allianceName, allianceName)) {
			flag = true;
		}
		
		if(!StringUtils.equals(this.allianceAbbr, allianceAbbr)) {
			flag = true;
		}
		
		if(!StringUtils.equals(this.allianceTitle, allianceTitle)) {
			flag = true;
		}
		
		if(!StringUtils.equals(this.allianceTitleName, allianceTitleName)) {
			flag = true;
		}
		
		if(!StringUtils.equals(this.governmentTitle, governmentTitle)) {
			flag = true;
		}
		
		if(vip != roleInfo.getVipInfo().getVipLevel()) {
			flag = true;
		}
		
		if(flag) {
			return flag;
		}
		
		return false;
	}
	
	public Long getId() {
		return playerId;
	}

	public void setId(Long k) {
		this.playerId = k;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMilitaryLevel() {
		return militaryLevel;
	}

	public void setMilitaryLevel(int militaryLevel) {
		this.militaryLevel = militaryLevel;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(int buildLevel) {
		this.buildLevel = buildLevel;
	}

	public String getAllianceAbbr() {
		return allianceAbbr;
	}

	public void setAllianceAbbr(String allianceAbbr) {
		this.allianceAbbr = allianceAbbr;
	}

	public String getAllianceTitle() {
		return allianceTitle;
	}

	public void setAllianceTitle(String allianceTitle) {
		this.allianceTitle = allianceTitle;
	}

	public String getGovernmentTitle() {
		return governmentTitle;
	}

	public void setGovernmentTitle(String governmentTitle) {
		this.governmentTitle = governmentTitle;
	}

	public String getAllianceTitleName() {
		return allianceTitleName;
	}

	public void setAllianceTitleName(String allianceTitleName) {
		this.allianceTitleName = allianceTitleName;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("playerId", playerId);
		jbaseData.put("userName", userName);
		jbaseData.put("serverId", serverId);
		jbaseData.put("name", name);
		jbaseData.put("img", img);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("allianceName", allianceName);
		jbaseData.put("vip", vip);
		jbaseData.put("level", level);
		jbaseData.put("militaryLevel", militaryLevel);
		jbaseData.put("updateTime", updateTime);
		jbaseData.put("buildLevel", buildLevel);
		jbaseData.put("allianceAbbr", allianceAbbr);
		jbaseData.put("allianceTitle", allianceTitle);
		jbaseData.put("governmentTitle", governmentTitle);
		jbaseData.put("allianceTitleName", allianceTitleName);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.playerId = jBaseData.getLong("playerId", 0);
		this.userName = jBaseData.getString("userName", "");
		this.serverId = jBaseData.getInt("serverId", 0);
		this.name = jBaseData.getString("name", "");
		this.img = jBaseData.getString("img","");
		this.allianceId = jBaseData.getLong("allianceId", 0);
		this.allianceName = jBaseData.getString("allianceName","");
		this.vip = jBaseData.getInt("vip", 0);
		this.level = jBaseData.getInt("level", 0);
		this.militaryLevel = jBaseData.getInt("militaryLevel", 0);
		this.updateTime = jBaseData.getLong("updateTime", 0);
		this.buildLevel = jBaseData.getInt("buildLevel", 0);
		this.allianceAbbr = jBaseData.getString("allianceAbbr","");
		this.allianceTitle = jBaseData.getString("allianceTitle","");
		this.governmentTitle = jBaseData.getString("governmentTitle","");
		this.allianceTitleName = jBaseData.getString("allianceTitleName","");
	}
}
