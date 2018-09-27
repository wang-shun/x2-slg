package com.xgame.logic.server.core.db.redis;

import com.xgame.logic.server.core.sysconfig.RedisServerInfo;
import com.xgame.logic.server.core.utils.InjectorUtil;

import redis.clients.jedis.Jedis;


/**
 * 跨服的请求
 * @author jacky.jiang
 *
 */
public class CrossRedisClient extends AbstractReidsClient {
	
	private Jedis jedis;
	
	public CrossRedisClient(int serverId) {
		RedisServerInfo redisServerInfo = InjectorUtil.getInjector().redisServerConfig.getRedisServerInfo(serverId);
		if(redisServerInfo != null) {
			jedis = new Jedis(redisServerInfo.getIp(), redisServerInfo.getPort(), InjectorUtil.getInjector().redisTimeout);
		}
	}

	@Override
	public Jedis getDbClient() {
		return jedis;
	}

	@Override
	public void release(Jedis jedis) {
		jedis.close();
	}

	@Override
	public void returnBroken(Jedis jedis) {
		jedis.close();
	}
}
