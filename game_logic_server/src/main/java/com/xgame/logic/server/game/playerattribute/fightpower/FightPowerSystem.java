package com.xgame.logic.server.game.playerattribute.fightpower;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;

/**
 *
 *2016-12-26  18:21:43
 *@author ye.yuan
 *
 */
@Component
public class FightPowerSystem extends CacheProxy<FightPower> {

	@Override
	public Class<?> getProxyClass() {
		return FightPower.class;
	}

	public void reset(long playerId, FightPowerKit documentUtil,Number value){
		FightPower orCreate = getOrCreate(playerId);
		orCreate.getNotes().put(documentUtil.getId(), value);	
		InjectorUtil.getInjector().dbCacheService.update(orCreate);
	}
	
	public Number getOrCreateValue(long playerId, FightPowerKit documentUtil){
		return getOrCreate(playerId).getNotes().get(documentUtil.getId());
	}
	
	public FightPower getOrCreate(long playerId) {
		FightPower document = InjectorUtil.getInjector().dbCacheService.get(FightPower.class, playerId);
		if(document == null) {
			document = new FightPower(playerId);
			document = InjectorUtil.getInjector().dbCacheService.create(document);
		}
		return document;
	}
	
}
