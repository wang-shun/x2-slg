package com.xgame.logic.server.game.war.entity.callback;

import com.xgame.logic.server.game.war.entity.Battle;


/**
 * 战斗回调
 * @author jacky.jiang
 *
 */
public interface IBattleCallback {
	
	/**
	 * 战斗回调
	 * @param battle
	 */
	public void callback(Battle battle);
}
