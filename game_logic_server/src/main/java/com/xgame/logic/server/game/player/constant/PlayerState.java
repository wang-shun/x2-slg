package com.xgame.logic.server.game.player.constant;

import com.xgame.logic.server.game.player.entity.Player;

/**
 * Created by vyang on 6/21/16.
 */
public enum PlayerState {
	LOGIN(0), //
//	RTS(1), //
//	PVE_GROUP(2), //
//	PVP(3), //
//	SESSION_CREATE(4), //
//	ARENA(6), //
//	AUTHING(7), //
	GAME(8),
	OFFLINE(9),
	TEMP_OFFLINE(10),
	;

	private int id;
	
	PlayerState(int id){
		this.id=id;
	}
	
	public boolean happening(Player player) {
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
