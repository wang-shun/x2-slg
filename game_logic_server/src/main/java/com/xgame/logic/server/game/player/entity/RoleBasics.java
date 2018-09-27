package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * Created by vyang on 6/21/16.
 */
public class RoleBasics implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private long roleId; // 角色Id
	
	@Tag(2)
	private int serverArea; // 当前服
	
	@Tag(3)
	private int lastServerKey = -1;// 之前所在服
	
	@Tag(4)
	private String roleName; // 角色名
	
	@Tag(5)
	private String roleKey; // 角色唯一代码,GM使用,用户可见
	
	@Tag(6)
	private int level; // 角色等级
	
	@Tag(7)
	private String headImg; // 头像
	
	@Tag(8)
	private int avatar; // 头像
	
	@Tag(10)
	private long createTime; // 创建时间
	
	@Tag(11)
	private long deleteTime; // 删除时间
	
	@Tag(12)
	private long loginTime; // 登录时间
	
	@Tag(13)
	private long logoutTime;// 登出时间
	
	@Tag(14)
	private int tx = -1;// 在当前服的位置
	
	@Tag(15)
	private int ty = -1;// 在当前服的位置
	
	@Tag(17)
	private String userName; // user name
	
	@Tag(18)
	private long attackTimer; // 被打时间
	
	@Tag(19)
	private long onlineUpdateTime;//更新在线时间时间戳
	
	@Tag(20)
	private long onlineTime;//累计在线时间

	private long spriteId; // 玩家精灵id
	
	@Tag(17)
	private boolean isNew = true;
	
	@Tag(23)
	private int fightStatus;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getServerArea() {
		return serverArea;
	}

	public void setServerArea(int serverArea) {
		this.serverArea = serverArea;
	}

	public int getLastServerKey() {
		return lastServerKey;
	}

	public void setLastServerKey(int lastServerKey) {
		this.lastServerKey = lastServerKey;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(long deleteTime) {
		this.deleteTime = deleteTime;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getTx() {
		return tx;
	}

	public void setTx(int tx) {
		this.tx = tx;
	}

	public int getTy() {
		return ty;
	}

	public void setTy(int ty) {
		this.ty = ty;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getAttackTimer() {
		return attackTimer;
	}

	public void setAttackTimer(long attackTimer) {
		this.attackTimer = attackTimer;
	}

	public long getOnlineUpdateTime() {
		return onlineUpdateTime;
	}

	public void setOnlineUpdateTime(long onlineUpdateTime) {
		this.onlineUpdateTime = onlineUpdateTime;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public long getSpriteId() {
		return spriteId;
	}

	public void setSpriteId(long spriteId) {
		this.spriteId = spriteId;
	}
	
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public int getFightStatus() {
		return fightStatus;
	}

	public void setFightStatus(int fightStatus) {
		this.fightStatus = fightStatus;
	}

	public long refreshOnlineTime(){
		if(onlineUpdateTime == 0){
			onlineUpdateTime = loginTime;
		}
		long now = System.currentTimeMillis();
		long time0 = now - onlineUpdateTime;
		onlineTime += time0;
		onlineUpdateTime = now;
		return time0;
	}
	
	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("roleId", roleId);
		jBaseData.put("serverArea", serverArea);
		jBaseData.put("lastServerKey", lastServerKey);
		
		jBaseData.put("roleName", roleName);
		jBaseData.put("roleKey", roleKey);
		jBaseData.put("level", level);
		
		jBaseData.put("headImg", headImg);
		jBaseData.put("avatar", avatar);
		jBaseData.put("createTime", createTime);
		
		jBaseData.put("deleteTime", deleteTime);
		jBaseData.put("loginTime", loginTime);
		jBaseData.put("logoutTime", logoutTime);
		
		jBaseData.put("tx", tx);
		jBaseData.put("ty", ty);
		jBaseData.put("userName", userName);
		
		jBaseData.put("attackTimer", attackTimer);
		jBaseData.put("onlineUpdateTime", onlineUpdateTime);
		jBaseData.put("onlineTime", onlineTime);
		
		jBaseData.put("spriteId", spriteId);
		jBaseData.put("isNew", isNew);
		jBaseData.put("fightStatus", fightStatus);
		return jBaseData;
	}
	
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.roleId = jBaseData.getLong("roleId", 0);
		this.serverArea = jBaseData.getInt("serverArea", 0);
		this.lastServerKey = jBaseData.getInt("lastServerKey", 0);
		
		this.roleName = jBaseData.getString("roleName", "");
		this.roleKey = jBaseData.getString("roleKey", "");
		this.level = jBaseData.getInt("level", 0);
		
		this.headImg = jBaseData.getString("headImg", "");
		this.avatar = jBaseData.getInt("avatar", 0);
		this.createTime = jBaseData.getLong("createTime", 0);
		
		this.deleteTime = jBaseData.getLong("deleteTime", 0);
		this.loginTime = jBaseData.getLong("loginTime", 0);
		this.logoutTime = jBaseData.getLong("logoutTime", 0);
		
		this.tx = jBaseData.getInt("tx", 0);
		this.ty = jBaseData.getInt("ty", 0);
		this.userName = jBaseData.getString("userName", "");
		
		this.attackTimer = jBaseData.getLong("attackTimer", 0);
		this.onlineUpdateTime = jBaseData.getLong("onlineUpdateTime", 0);
		this.onlineTime = jBaseData.getLong("onlineTime", 0);
		
		this.spriteId = jBaseData.getLong("spriteId", 0);
		this.isNew = jBaseData.getBoolean("isNew", true);
		this.fightStatus = jBaseData.getInt("fightStatus", 0);
	}

}
