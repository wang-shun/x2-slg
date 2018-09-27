package com.xgame.logic.server.game.email.data;

import java.io.Serializable;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;

import io.protostuff.Tag;

/**
 *
 *2017-1-10  14:55:23
 *@author ye.yuan
 *
 */
public class TalkMailData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 目标位置
	 */
	@Tag(1)
	private Vector2Bean targetPosition;
	@Tag(2)
	private long activePlayerId;
	@Tag(3)
	private long passPlayerId;
	/**
	 * 精灵类型
	 */
	@Tag(4)
	private int spriteType;
	
	public Player getActivePlayer() {
		return InjectorUtil.getInjector().playerManager.get(activePlayerId);
	}

	public Player getPassPlayer() {
		return InjectorUtil.getInjector().playerManager.get(passPlayerId);
	}

	public Vector2Bean getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector2Bean targetPosition) {
		this.targetPosition = targetPosition;
	}

	public long getActivePlayerId() {
		return activePlayerId;
	}

	public void setActivePlayerId(long activePlayerId) {
		this.activePlayerId = activePlayerId;
	}

	public long getPassPlayerId() {
		return passPlayerId;
	}

	public void setPassPlayerId(long passPlayerId) {
		this.passPlayerId = passPlayerId;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}
	
}
