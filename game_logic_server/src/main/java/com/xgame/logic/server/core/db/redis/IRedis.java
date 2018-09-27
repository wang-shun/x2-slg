package com.xgame.logic.server.core.db.redis;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 操作
 * @author jacky.jiang
 *
 */
public interface IRedis {
	
	/**
	 * 判断redis连接是否可用
	 * @return
	 */
	boolean isAvailable();
	
	/**
	 * 批量保存
	 * @param map
	 */
	void saveBatch(Map<String, ByteBuffer> map);
	
	/**
	 * 根据key不分匹配
	 * @param keyPattern
	 * @return
	 */
	List<Object> queryKeyPattern(String keyPattern);
	
	/**
	 * 批量列表查询(启动加载数据)
	 * @param keys
	 * @return
	 */
	List<Object> queryBatch(Set<String> keys);
	
	/**
	 * 原子自增
	 * @param key
	 * @return
	 */
	long incr(String key);
	
	/**
	 * 增加某个特定值
	 * @param key
	 * @param value
	 * @return
	 */
	long incrBy(String key, long value);
	
	/**
	 * 保存
	 * @param key
	 * @param data
	 */
	void save(String key, ByteBuffer data);
	
	/**
	 * 查询
	 * @param key
	 * @return
	 */
	ByteBuffer quary(String key);
	
	/**
	 * set集合设置
	 * @param key
	 * @param filed
	 * @param data
	 */
	void hset(String key, String filed, ByteBuffer data);
	
	/**
	 * 获取set集合当中某个值
	 * @param key
	 * @param filed
	 * @return
	 */
	ByteBuffer hget(String key, String filed);
	
	/**
	 * 返回列表
	 * @param key
	 * @return
	 */
	List<ByteBuffer> hvals(String key);
	
	/**
	 * 移除某个key
	 * @param key
	 */
	void remove(String key);
	
	/**
	 * key列表
	 * @param keyPatterns
	 * @return
	 */
	Set<String> keys(String keyPatterns);
	 
}
