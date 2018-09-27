package com.xgame.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * redis操作类
 * @author jacky.jiang
 *
 */
public abstract class AbstractReidsClient {

//	public void saveBatch(List<IEntity<?>> entityList) {
//		Jedis jedis = getDbClient();
//		Pipeline pipeline = jedis.pipelined();
//		try {
//			for (IEntity<?> entity : entityList) {
//				pipeline.hset(entity.getClass().getSimpleName(), String.valueOf(entity.getId()), JsonUtil.toJSON(entity));
//			}
//			
//			pipeline.sync();
//		} catch (Exception e) {
//			returnBroken(jedis);
//		} finally {
//			release(jedis);
//		}
//	}
//	
//	public <T> List<T> queryBatch(Class<T> clazz, Set<String> keys) {
//		Jedis jedis = getDbClient();
//		try {
//			Pipeline pipeline = jedis.pipelined();
//			if(keys != null && !keys.isEmpty()) {
//				for(String key : keys) {
//					pipeline.hget(clazz.getSimpleName(), key);
//				}
//			} else {
//				return null;
//			}
//			
//			// 返回
//			List<Object> list = pipeline.syncAndReturnAll();
//			List<T> resultList = new ArrayList<>();
//			for (Object obj : list) {
//				if (obj != null) {
//					String value = (String) obj;
//					T t = JsonUtil.fromJSON(value, clazz);
//					resultList.add(t);
//				}
//			}
//			
//			return resultList;
//		}catch(Exception e){
//			returnBroken(jedis);
//			return null;
//		} finally {
//			release(jedis);
//		}
//	}

	public long incr(String key) {

		Jedis jedis = getDbClient();
		try {
			return jedis.incr(key);	
		} catch(Exception e){
			returnBroken(jedis);
		}finally {
			release(jedis);
		}
		return -1;
	}
	
	public Set<String> keys(String keyPatterns) {
		
		Jedis jedis = getDbClient();
		try {
			return jedis.keys(keyPatterns);
		} catch(Exception e){
			returnBroken(jedis);
			return null;
		}finally {
			release(jedis);
		}
	}
	
	/**
	 * 向list添加元素
	 * @param key
	 * @param objects
	 */
	public void lpush(String key, String... objects) {
		
		Jedis jedis = getDbClient();
		try {
			jedis.lpush(key, objects);
		} catch(Exception e){
			returnBroken(jedis);
		}finally {
			release(jedis);
		}
	}
	
	/**
	 * 保留数据的长度
	 * @param key
	 * @param length
	 */
	public void ltrim(String key, int length) {
		
		Jedis jedis = getDbClient();
		try {
			jedis.ltrim(key, 0, length);
		} catch(Exception e){
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 向队列尾部添加元素
	 * @param key
	 * @param objects
	 */
	public void rpush(String key, String... objects) {
		
		Jedis jedis = getDbClient();
		try {
			jedis.rpush(key, objects);
		} catch(Exception e){
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		
	}
	
	/**
	 * 移除队列元素
	 * @param key
	 */
	public void remove(String key, String value) {
		
		Jedis jedis = getDbClient();
		try {
			jedis.lrem(key, 0, value);
		} catch(Exception e){
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 移除队列元素
	 * @param key
	 */
	public List<String> lrang(String key, int start, int size) {
		Jedis jedis = getDbClient();
		try {
			return jedis.lrange(key, start, start + size);
		} catch(Exception e){
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 移除队列元素
	 * @param key
	 */
	public List<String> r(String key, int start, int size) {
		Jedis jedis = getDbClient();
		try {
			return jedis.lrange(key, start, start + size);
		} catch(Exception e){
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 保存信息
	 * @param key
	 * @param obj
	 */
//	public void hMset(String key, List<IEntity<?>> update) {
//		Jedis jedis = getDbClient();
//		try {
//			Map<String, String> jsonMap = new HashMap<>();
//			for(IEntity<?> entity : update) {
//				jsonMap.put(String.valueOf(entity.getId()), JsonUtil.toJSON(entity));
//			}
//			jedis.hmset(key, jsonMap);
//		} catch(Exception e){
//			returnBroken(jedis);
//		} finally {
//			release(jedis);
//		}
//	}
	
	/**
	 * 获取map当中的值
	 * @param key
	 * @param filed
	 * @return
	 */
//	public <T> T hget(Class<T> clazz, String key, Serializable field) {
//		
//		Jedis jedis = getDbClient();
//		try {
//			String value = jedis.hget(key, String.valueOf(field));
//			if (value != null) {
//				T t = JsonUtil.fromJSON(value, clazz);
//				return t;
//			} else {
//				return null;
//			}
//		} catch(Exception e){
//			returnBroken(jedis);
//			return null;
//		}finally {
//			release(jedis);
//		}
//	}
	
	public String hget(String key, Serializable field) {
		Jedis jedis = getDbClient();
		try {
			String value = jedis.hget(key, String.valueOf(field));
			return value;
		} catch (Exception e) {
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取map当中的值
	 * @param key
	 * @param filed
	 * @return
	 */
//	public <T> T hget(Class<T> clazz, Serializable field) {
//		
//		Jedis jedis = getDbClient();
//		try {
//			String value = jedis.hget(clazz.getSimpleName(), String.valueOf(field));
//			if (value != null) {
//				T t = JsonUtil.fromJSON(value, clazz);
//				return t;
//			} else {
//				return null;
//			}
//		} catch(Exception e){
//			returnBroken(jedis);
//			return null;
//		}finally {
//			release(jedis);
//		}
//	}
	
	/**
	 * 设置map当中的值
	 * @param entity
	 */
//	public void hset(IEntity<?> entity) {
//		Jedis jedis = getDbClient();
//		try {
//			jedis.hset(entity.getClass().getSimpleName(), String.valueOf(entity.getId()), JsonUtil.toJSON(entity));
//		} catch(Exception e) {
//			returnBroken(jedis);
//		} finally {
//			release(jedis);
//		}
//	}
	
	/**
	 * 设置map当中的值
	 * @param key
	 * @param field
	 * @param obj
	 */
	public void hset(String key, Serializable field, String obj) {
		Jedis jedis = getDbClient();
		try {
			jedis.hset(key, String.valueOf(field), obj);
		} catch(Exception e) {
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 查询map当中key的值
	 * @param clazz
	 * @param key
	 * @return
	 */
//	public <T> List<T> hvals(Class<T> clazz, String key) {
//		Jedis jedis = getDbClient();
//		try {
//			List<String> valueList = jedis.hvals(key);
//			if(valueList != null && !valueList.isEmpty()) {
//				List<T> list = new ArrayList<>();
//				for(String value : valueList) {
//					if(!StringUtils.isEmpty(value)) {
//						T t  = null;
//						t = JsonUtil.fromJSON(value, clazz);
//						list.add(t);
//					}
//				}
//				return list;
//			}
//			return null;
//		} catch(Exception e) {
//			returnBroken(jedis);
//			return null;
//		} finally {
//			release(jedis);
//		}
//	}
	
	/**
	 * 查询map当中key的值
	 * @param clazz
	 * @return
	 */
//	public <T> List<T> hvals(Class<T> clazz) {
//		Jedis jedis = getDbClient();
//		try {
//			List<String> valueList = jedis.hvals(clazz.getSimpleName());
//			if(valueList != null && !valueList.isEmpty()) {
//				List<T> list = new ArrayList<>();
//				for(String value : valueList) {
//					if(!StringUtils.isEmpty(value)) {
//						T t = JsonUtil.fromJSON(value, clazz);
//						list.add(t);	
//					}
//				}
//				return list;
//			}
//			return null;
//		} catch(Exception e) {
//			returnBroken(jedis);
//			return null;
//		} finally {
//			release(jedis);
//		}
//	}
	
	/**
	 * 删除set当中元素
	 * @param key
	 * @param objects
	 */
	public void hdel(String key, Serializable field) {

		Jedis jedis = getDbClient();
		try {
			jedis.hdel(key, String.valueOf(field));
		} catch(Exception e) {
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	
	/**
	 * 删除set当中元素
	 * @param key
	 * @param objects
	 */
	public void hdel(Class<?> clazz, Serializable field) {

		Jedis jedis = getDbClient();
		try {
			jedis.hdel(clazz.getSimpleName(), String.valueOf(field));
		} catch(Exception e) {
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取hashes中制定key的所有键值
	 * @param key
	 * @return
	 */
	public Set<String> hkeys(String key){
		Jedis jedis = getDbClient();
		try{
			return jedis.hkeys(key);
		} catch(Exception e) {
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 添加到set当中
	 * @param key
	 * @param value
	 */
	public void sadd(String key, String value) {	
		Jedis jedis = getDbClient();
		try {
			jedis.sadd(key, value);
		} catch(Exception e) {
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 移除set当中的元素
	 * @param key
	 * @param value
	 */
	public void srem(String key, String value) {
		Jedis jedis = getDbClient();
		try {
			jedis.srem(key, new String[]{value});
		} catch(Exception e) {
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 返回集合set
	 * @param key
	 * @return
	 */
	public Set<String> smember(String key) {
		Jedis jedis = getDbClient();
		try {
			return jedis.smembers(key);
		} catch(Exception e) {
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 判断是否是集合set当中的元素
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member) {
		Jedis jedis = getDbClient();
		try {
			return jedis.sismember(key, member);
		} catch(Exception e) {
			returnBroken(jedis);
			return false;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public abstract Jedis getDbClient();
	
	/**
	 * 释放连接
	 * @param jedis
	 */
	public abstract void release(Jedis jedis);
	
	/**
	 * 异常释放连接
	 * @param jedis
	 */
	public abstract void returnBroken(Jedis jedis);
}
