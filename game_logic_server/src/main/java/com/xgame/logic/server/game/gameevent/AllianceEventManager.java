package com.xgame.logic.server.game.gameevent;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.gameevent.entity.AllianceEvent;

@Component
public class AllianceEventManager extends CacheProxy<AllianceEvent> {

	@Override
	public Class<?> getProxyClass() {
		return AllianceEvent.class;
	}
	
	/**
	 * 获取当前联盟事件信息
	 * @param playerId
	 * @return
	 */
	public AllianceEvent getOrCreate(long allianceId){
		AllianceEvent allianceEvent = InjectorUtil.getInjector().dbCacheService.get(AllianceEvent.class, allianceId);
		if (allianceEvent == null) {
			allianceEvent = new AllianceEvent();
			allianceEvent.setAllianceId(allianceId);
			InjectorUtil.getInjector().dbCacheService.create(allianceEvent);
		}
		
		return allianceEvent;
	}
}
