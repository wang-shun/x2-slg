package com.xgame.logic.server.core.db.redis;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.logic.server.core.db.redis.pool.RedisClientPool;

import redis.clients.jedis.Jedis;

/**
 * redis 客户端
 * @author jacky.jiang
 *
 */
@Component
public class RedisClient extends AbstractReidsClient {
	
	@Autowired
	private RedisClientPool redisClientPool;

	@PostConstruct
	public void init() {
		redisClientPool.init();
	}
	
	@Override
	public Jedis getDbClient() {
		return redisClientPool.getJedis();
	}

	@Override
	public void release(Jedis jedis) {
		redisClientPool.returnResource(jedis);
	}

	@Shutdown(order = ShutdownOrder.REDIS_CLIENT, desc = "数据库停服")
	public void stop() {
		redisClientPool.close();
	}

	@Override
	public void returnBroken(Jedis jedis) {
		redisClientPool.returnResource(jedis);
	}

}
