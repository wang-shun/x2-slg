package com.xgame.logic.server.game.cross.entity;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 * 
 * @author jacky.jiang
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrossPlayer {
	
	/**
	 * 玩家简易信息
	 */
	private SimpleRoleInfo simpleRoleInfo;
	
	/**
	 * 玩家session
	 */
	private PlayerSession playerSession;
	
	/**
	 * 初始化
	 * @param simpleRoleInfo
	 */
	public void init(SimpleRoleInfo simpleRoleInfo) {
		this.simpleRoleInfo = simpleRoleInfo;
	}

	public SimpleRoleInfo getSimpleRoleInfo() {
		return simpleRoleInfo;
	}

	public void setSimpleRoleInfo(SimpleRoleInfo simpleRoleInfo) {
		this.simpleRoleInfo = simpleRoleInfo;
	}

	public PlayerSession getPlayerSession() {
		return playerSession;
	}

	public void setPlayerSession(PlayerSession playerSession) {
		this.playerSession = playerSession;
	}
	
	public int getServerId() {
		return this.simpleRoleInfo.getServerId();
	}
	
	public long getId() {
		return this.simpleRoleInfo.getId();
	}
	
	public String toCrossPlayerJson() {
		return JsonUtil.toJSON(this.simpleRoleInfo);
	}
}
