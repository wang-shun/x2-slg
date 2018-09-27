package com.xgame.redis;

import redis.clients.jedis.Jedis;


/**
 * 跨服的请求
 * @author jacky.jiang
 *
 */
public class CrossRedisClient extends AbstractReidsClient {
	
	private Jedis jedis;
	
	public CrossRedisClient(int serverId) {
		RedisServerInfo redisServerInfo = RedisConfig.getRedisServerInfo(serverId);
		if(redisServerInfo != null) {
			jedis = new Jedis(redisServerInfo.getIp(), redisServerInfo.getPort(), 60000);
			if(redisServerInfo.getPass() != null){
				jedis.auth(redisServerInfo.getPass());
			}
		}
	}
	
	public CrossRedisClient(RedisServerInfo redisServerInfo) {
		jedis = new Jedis(redisServerInfo.getIp(), redisServerInfo.getPort(), 60000);
		if(redisServerInfo.getPass() != null){
			jedis.auth(redisServerInfo.getPass());
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
