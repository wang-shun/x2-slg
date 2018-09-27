package com.xgame.logic.server.game.gameevent;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.gameevent.entity.PlayerEvent;

@Component
public class PlayerEventManager extends CacheProxy<PlayerEvent> {

	@Override
	public Class<?> getProxyClass() {
		return PlayerEvent.class;
	}
	
	/**
	 * 获取当前玩家事件信息
	 * @param playerId
	 * @return
	 */
	public PlayerEvent getOrCreate(long playerId){
		PlayerEvent playerEvent = InjectorUtil.getInjector().dbCacheService.get(PlayerEvent.class, playerId);
		if (playerEvent == null) {
			playerEvent = new PlayerEvent();
			playerEvent.setPlayerId(playerId);
			InjectorUtil.getInjector().dbCacheService.create(playerEvent);
		}
		
		return playerEvent;
	}
}
