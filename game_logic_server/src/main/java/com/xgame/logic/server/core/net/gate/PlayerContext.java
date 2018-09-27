package com.xgame.logic.server.core.net.gate;

import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 玩家上线文对象
 * @author jacky.jiang
 *
 */ 
public class PlayerContext {
	
	private Player player;
	private PlayerSession playerSession;

	public static PlayerContext valueOf(Player player, PlayerSession playerSession) {
		PlayerContext playerContext = new PlayerContext();
		playerContext.setPlayer(player);
		playerContext.setPlayerSession(playerSession);
		return playerContext;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerSession getPlayerSession() {
		return playerSession;
	}

	public void setPlayerSession(PlayerSession playerSession) {
		this.playerSession = playerSession;
	}
	
}
