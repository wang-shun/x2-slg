package com.xgame.logic.server.core.db.redis.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 初始化连接
 * 
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class GlobalRedisPool {

	@Value("${game.dbAddress}")
	private String ADDR;
	@Value("${game.dbPort}")
	private int PORT;
	@Value("${game.global.maxActive}")
	private int MAX_ACTIVE;
	@Value("${game.global.maxIdle}")
	private int MAX_IDLE;
	@Value("${game.global.maxWait}")
	private int MAX_WAIT;
	@Value("${game.global.testOnBorrow}")
	private boolean TEST_ON_BORROW;
	@Value("${game.dbPass}")
	private String pass;
	
	/**
	 * redis连接池
	 */
	private static  JedisPool jedisPool;
	
	/** 
	* 初始化Redis连接池 
	*/
	public void init() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(300);
			config.setMaxIdle(100);
			config.setMinEvictableIdleTimeMillis(60 * 1000);
			config.setMaxWaitMillis(3000);
			config.setTimeBetweenEvictionRunsMillis(30 * 1000);
			config.setNumTestsPerEvictionRun(-1);
			config.setTestOnBorrow(true);
			config.setTestOnReturn(true);
			config.setTestWhileIdle(true);
			config.setBlockWhenExhausted(false);
			jedisPool = new JedisPool(config, ADDR, PORT, 60 * 1000);
		} catch (Exception e) {
			log.error("初始化连接池失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized Jedis getJedis() {
		Jedis resource = null;
		try {
			if (jedisPool != null) {
				resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("初始化连接池失败", e);
			e.printStackTrace();
			if(resource  != null) {
				resource.close();
			}
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	/**
	 * 关闭连接
	 * @param jedis
	 */
	public void returnBrokenResource(Jedis jedis) {
		if(jedis != null) {
			jedis.close();
		}
	}
	
	/**
	 * 关闭
	 */
	public void close() {
		jedisPool.close();
	}
}
