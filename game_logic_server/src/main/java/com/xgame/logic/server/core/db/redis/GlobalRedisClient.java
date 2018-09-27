package com.xgame.logic.server.core.db.redis;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.logic.server.core.db.redis.pool.GlobalRedisPool;

import redis.clients.jedis.Jedis;

/**
 * 全局redis客户端
 * @author jacky.jiang
 *
 */
@Component
public class GlobalRedisClient extends AbstractReidsClient {

	@Autowired
	private GlobalRedisPool redisPool;
	@Value("${game.global.dbAddress}")
	private String ADDR;
	@Value("${game.global.dbPort}")
	private int PORT;
	@Value("${game.dbPass}")
	private String pass;
	
//	private Jedis lockInstance;
	
	/**
	 * 获取超时
	 */
	public static final int TIMEOUT_MSECS = 3000;
	
	/**
	 * 占用锁超时时间
	 */
	public static final int EXPIRE_MSECS = 10000;
	
	/**
	 * 添加房间锁
	 */
//	private final Map<String, JedisLock> redisLockMap = new ConcurrentHashMap<String, JedisLock>(); 
	
	@PostConstruct
	public void init() {
//		lockInstance = new Jedis(ADDR, PORT, 60 * 1000);
//		lockInstance.auth(pass);
		redisPool.init();
	}
	
//	/**
//	 * 获取锁
//	 * @param playerId
//	 * @return
//	 */
//	public JedisLock getLock(Lockable<?> lockEntity) {
//		String key = DBKey.getLockKey(lockEntity.getClass(), lockEntity.getIdentity());
//		JedisLock obj = redisLockMap.get(key);
//		if(obj == null) {
//			obj = new JedisLock(lockInstance, key.intern(), GlobalRedisClient.TIMEOUT_MSECS, GlobalRedisClient.EXPIRE_MSECS);
//			redisLockMap.put(key, obj);
//		}
//		return obj;
//	}
//	
//	/**
//	 * 移除锁
//	 * @param lockEntity
//	 */
//	public void removeLock(Lockable<?> lockEntity) {
//		String key = DBKey.getLockKey(lockEntity.getClass(), lockEntity.getIdentity());
//		redisLockMap.remove(key);
//	}

	@Override
	public Jedis getDbClient() {
		return redisPool.getJedis();
	}

	@Override
	public void release(Jedis jedis) {
		jedis.close();
	}

	@Shutdown(order = ShutdownOrder.GLOBAL_REDIS_CLIENT, desc = "redis数据关闭")
	public void stop() {
		redisPool.close();
	}

	@Override
	public void returnBroken(Jedis jedis) {
		jedis.close();
	}
}
