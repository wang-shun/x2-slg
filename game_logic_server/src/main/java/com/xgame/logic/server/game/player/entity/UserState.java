package com.xgame.logic.server.game.player.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserState {

	LOGINING(1),  
	OFFLINE(2),  
	GAMEING(3),  
//	NORMAL(3),
	DISABLE(4),
 
	;
	
	@Getter
	private int id;
}
