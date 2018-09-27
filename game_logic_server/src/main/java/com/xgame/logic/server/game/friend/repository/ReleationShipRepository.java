package com.xgame.logic.server.game.friend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.game.friend.entity.ReleationShip;

/**
 * 仓储管理
 * @author jacky.jiang
 *
 */
@Component
public class ReleationShipRepository {
	
	@Autowired
	private RedisClient redisClient;
	
	/**
	 * 获取关系列表
	 * @param playerId
	 * @return
	 */
	public ReleationShip getOrCreateReleationShip(long playerId) {
		ReleationShip releationShip = redisClient.hget(ReleationShip.class, playerId);
		if(releationShip == null) {
			releationShip = new ReleationShip();
			releationShip.setPlayerId(playerId);
			saveReleationShip(releationShip);
		}
		return releationShip;
	}
	
	public ReleationShip getReleationShip(long playerId) {
		ReleationShip releationShip = redisClient.hget(ReleationShip.class, playerId);
		return releationShip;
	}
	
	/**
	 * 保存好友关系
	 * @param releationShip
	 */
	public void saveReleationShip(ReleationShip releationShip) {
		redisClient.hset(releationShip);
	}
}
