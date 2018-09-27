package com.xgame.logic.server.game.radar.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;

import io.protostuff.Tag;

/**
 *
 *2016-12-22  09:44:52
 *@author ye.yuan
 *
 */
public abstract class RadarAction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	protected long activePlayerId;
	@Tag(2)
	protected long passPlayerId;
	@Tag(4)
	protected Vector2Bean loction;
	
	public RadarAction() {
		
	}
	
	public RadarAction(Player activePlayer,Player passPlayer, Vector2Bean vector2) {
		this.activePlayerId = activePlayer.getRoleId();
		this.passPlayerId=passPlayer.getRoleId();
		this.loction = vector2;
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
	
	public Vector2Bean getLoction() {
		return loction;
	}

	public void setLoction(Vector2Bean loction) {
		this.loction = loction;
	}

	public Player getActivePlayer() {
		return InjectorUtil.getInjector().playerManager.get(activePlayerId);
	}


	public Player getPassPlayer() {
		return InjectorUtil.getInjector().playerManager.get(passPlayerId);
	}

}
