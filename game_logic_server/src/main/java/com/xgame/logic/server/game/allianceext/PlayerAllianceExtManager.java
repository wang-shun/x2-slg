package com.xgame.logic.server.game.allianceext;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.allianceext.entity.PlayerAllianceExt;

@Component
public class PlayerAllianceExtManager extends CacheProxy<PlayerAllianceExt>{
	
	/**
	 * 获取玩家联盟数据
	 * @param playerId
	 * @return
	 */
	public PlayerAllianceExt getOrCreate(long playerId) {
		
		PlayerAllianceExt playerAllianceExt = InjectorUtil.getInjector().dbCacheService.get(PlayerAllianceExt.class, playerId);
		if (playerAllianceExt == null) {
			playerAllianceExt = new PlayerAllianceExt();
			playerAllianceExt.setPlayerId(playerId);
			playerAllianceExt.setRefreshTime(System.currentTimeMillis());
			InjectorUtil.getInjector().dbCacheService.create(playerAllianceExt);
		}
		
		synchronized(playerAllianceExt) {
			boolean update  = playerAllianceExt.refresh();
			if(update) {
				InjectorUtil.getInjector().dbCacheService.update(playerAllianceExt);
			}
		}
		
		return playerAllianceExt;
	}
	
	@Override
	public Class<?> getProxyClass() {
		return PlayerAllianceExt.class;
	}
}
