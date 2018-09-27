package com.xgame.logic.server.game.gameevent;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.gameevent.entity.EventMaxScore;

@Component
public class EventMaxScoreManager extends CacheProxy<EventMaxScore> {

	@Override
	public Class<?> getProxyClass() {
		return EventMaxScore.class;
	}
	
	/**
	 * 获取事件上次玩家最高积分
	 * @param eventId
	 * @return
	 */
	public EventMaxScore getEventMaxScore(long eventId){
		EventMaxScore eventMaxScore = InjectorUtil.getInjector().dbCacheService.get(EventMaxScore.class, eventId);
		return eventMaxScore;
	}
}
