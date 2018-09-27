package com.xgame.logic.server.core.db.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.utils.JsonUtil;

/**
 * redis操作类
 * @author jacky.jiang
 *
 */
@Slf4j
public abstract class AbstractReidsClient {

	public void saveBatch(List<IEntity<?>> entityList) {
		log.debug("db saveBatch ");
		Jedis jedis = getDbClient();
		Pipeline pipeline = jedis.pipelined();
		try {
			for (IEntity<?> entity : entityList) {
				pipeline.hset(entity.getClass().getSimpleName(), String.valueOf(entity.getId()), JsonUtil.toJSON(entity.toJBaseData()));
			}
			
			pipeline.sync();
		} catch (Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	public <T> List<T> queryBatch(Class<T> clazz, Set<String> keys) {
		
		log.debug("db queryBatch ");

		Jedis jedis = getDbClient();
		try {
			Pipeline pipeline = jedis.pipelined();
			if(keys != null && !keys.isEmpty()) {
				for(String key : keys) {
					pipeline.hget(clazz.getSimpleName(), key);
				}
			} else {
				return null;
			}
			
			// 返回
			List<Object> list = pipeline.syncAndReturnAll();
			List<T> resultList = new ArrayList<>();
			for (Object obj : list) {
				if (obj != null) {
					String value = (String) obj;
					T innerT = (T)clazz.newInstance();
					JBaseData jBaseData = JsonUtil.fromJSON(value, JBaseData.class);
					((JBaseTransform)innerT).fromJBaseData(jBaseData);
					resultList.add(innerT);
				}
			}
			return resultList;
		}catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}

	public long incr(String key) {

		log.debug("db incr :{}", key);

		Jedis jedis = getDbClient();
		try {
			return jedis.incr(key);	
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		}finally {
			release(jedis);
		}
		return -1;
	}

//	public long incrBy(String key, long value) {
//
//		log.debug("db incr :{}", key);
//
//		Jedis jedis = getDbClient();
//		try {
//			return jedis.incrBy(key, value);
//		} catch(Exception e){
//			log.error("数据库查询异常", e);
//			returnBroken(jedis);
//		}finally {
//			release(jedis);
//		}
//		return -1;
//	}
//
//	/**
//	 * 判断key是否存在
//	 * @param key
//	 * @param field
//	 * @return
//	 */
//	public boolean hexist(String key, Serializable field) {
//		
//		Jedis jedis = getDbClient();
//		try {
//			return jedis.hexists(key, String.valueOf(field));
//		}catch(Exception e){
//			log.error("数据库查询异常", e);
//			returnBroken(jedis);
//			return false;
//		} finally {
//			release(jedis);
//		}
//	}
//	
//	public void remove(String key) {
//		
//		Jedis jedis = getDbClient();
//		try {
//			jedis.del(key);	
//		}  catch(Exception e){
//			log.error("数据库查询异常", e);
//			returnBroken(jedis);
//		}finally {
//			release(jedis);
//		}
//	}
	
	//----------------------------------------------------------------------------分割列表处理---------------------------------------------------------
	
	public Set<String> keys(String keyPatterns) {
		
		Jedis jedis = getDbClient();
		try {
			return jedis.keys(keyPatterns);
		} catch(Exception e){
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		}finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取队列指定下标的元素
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index){
		Jedis jedis = getDbClient();
		try {
			return jedis.lindex(key, index);
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		}finally {
			release(jedis);
		}
		return null;
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
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取队列长度
	 * @param key
	 * @return
	 */
	public long llen(String key){
		Jedis jedis = getDbClient();
		try {
			return jedis.llen(key);
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 获取队列第一个元素
	 * @param key
	 * @return
	 */
	public String lpop(String key){
		Jedis jedis = getDbClient();
		try {
			return jedis.lpop(key);
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 获取队列最后一个元素
	 * @param key
	 * @return
	 */
	public String rpop(String key){
		Jedis jedis = getDbClient();
		try {
			return jedis.rpop(key);
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
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
	public void hMset(String key, List<IEntity<?>> update) {
		Jedis jedis = getDbClient();
		try {
			Map<String, String> jsonMap = new HashMap<>();
			for(IEntity<?> entity : update) {
				jsonMap.put(String.valueOf(entity.getId()), JsonUtil.toJSON(entity.toJBaseData()));
			}
			jedis.hmset(key, jsonMap);
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
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
	public <T> T hget(Class<T> clazz, String key, Serializable field) {
		
		Jedis jedis = getDbClient();
		try {
			String value = jedis.hget(key, String.valueOf(field));
			if (value != null) {
				JBaseData jBaseData = JsonUtil.fromJSON(value, JBaseData.class);
				T t = clazz.newInstance();
				((JBaseTransform)t).fromJBaseData(jBaseData);
				return t;
			} else {
				return null;
			}
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return null;
		}finally {
			release(jedis);
		}
	}
	
	public String hget(String key, Serializable field) {
		Jedis jedis = getDbClient();
		try {
			String value = jedis.hget(key, String.valueOf(field));
			return value;
		} catch (Exception e) {
			log.error("数据库查询异常", e);
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
	public <T> T hget(Class<T> clazz, Serializable field) {
		
		Jedis jedis = getDbClient();
		try {
			String value = jedis.hget(clazz.getSimpleName(), String.valueOf(field));
			if (value != null) {
				JBaseData jBaseData = JsonUtil.fromJSON(value, JBaseData.class);
				T t = clazz.newInstance();
				((JBaseTransform)t).fromJBaseData(jBaseData);
				return t;
			} else {
				return null;
			}
		} catch(Exception e){
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return null;
		}finally {
			release(jedis);
		}
	}
	
	/**
	 * 设置map当中的值
	 * @param entity
	 */
	public void hset(IEntity<?> entity) {
		Jedis jedis = getDbClient();
		try {
			jedis.hset(entity.getClass().getSimpleName(), String.valueOf(entity.getId()), JsonUtil.toJSON(entity.toJBaseData()));
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
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
			log.error("数据库查询异常", e);
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
	public <T> List<T> hvals(Class<T> clazz, String key) {
		Jedis jedis = getDbClient();
		try {
			List<String> valueList = jedis.hvals(key);
			if(valueList != null && !valueList.isEmpty()) {
				List<T> list = new ArrayList<>();
				for(String value : valueList) {
					if(!StringUtils.isEmpty(value)) {
						T t  = clazz.newInstance();
						JBaseData jBaseData  = JsonUtil.fromJSON(value, JBaseData.class);
						((JBaseTransform)t).fromJBaseData(jBaseData);
						list.add(t);
					}
				}
				return list;
			}
			return null;
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 查询map当中key的值
	 * @param clazz
	 * @return
	 */
	public <T> List<T> hvals(Class<T> clazz) {
		Jedis jedis = getDbClient();
		try {
			List<String> valueList = jedis.hvals(clazz.getSimpleName());
			if(valueList != null && !valueList.isEmpty()) {
				List<T> list = new ArrayList<>();
				for(String value : valueList) {
					if(!StringUtils.isEmpty(value)) {
						T t  = clazz.newInstance();
						JBaseData jBaseData  = JsonUtil.fromJSON(value, JBaseData.class);
						((JBaseTransform)t).fromJBaseData(jBaseData);
						list.add(t);
					}
				}
				return list;
			}
			return null;
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return null;
		} finally {
			release(jedis);
		}
	}
	
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	public void delBatch(Class<?> clazz, List<String> fields) {
		Jedis jedis = getDbClient();
		try {
			jedis.hdel(clazz.getSimpleName(), fields.toArray(new String[]{}));
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
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
			log.error("数据库查询异常", e);
			returnBroken(jedis);
			return false;
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 添加sorted sets的操作方法 用于排序
	 */
	/**
	 * 将map添加或更新集合中数据
	 * @param key
	 * @param scoreMembers
	 */
	public void zadd(String key ,Map<String,Double> scoreMembers){
		Jedis jedis = getDbClient();
		try {
			jedis.zadd(key, scoreMembers);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 添加或更新单个元素
	 * @param key
	 * @param score
	 * @param member
	 */
	public void zadd(String key,Double score,String member){
		Jedis jedis = getDbClient();
		try {
			jedis.zadd(key, score, member);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 获取区间内集合元素数目
	 * @param key
	 * @return
	 */
	public long zcount(String key,double min ,double max){
		Jedis jedis = getDbClient();
		try {
			return jedis.zcount(key, min, max);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 删除集合区间内的元素
	 * @param key
	 * @param start
	 * @param end
	 */
	public void zremrangebyrank(String key,long start,long end){
		Jedis jedis = getDbClient();
		try {
			jedis.zremrangeByRank(key, start, end);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 对单个元素加指定值
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public double zincrby(String key, double score, String member){
		Jedis jedis = getDbClient();
		try {
			return jedis.zincrby(key, score, member);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 获取集合大小
	 * @param key
	 * @return
	 */
	public long zcard(String key){
		Jedis jedis = getDbClient();
		try {
			return jedis.zcard(key);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 倒排集合
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key,long start,long end){
		Jedis jedis = getDbClient();
		try {
			return jedis.zrevrange(key, start, end);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 获取倒排序列的区间分值对
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Tuple> zrevrangewithscore(String key,long start,long end){
		Jedis jedis = getDbClient();
		try {
			return jedis.zrevrangeWithScores(key, start, end);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 顺序区间集合
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key,long start,long end){
		Jedis jedis = getDbClient();
		try {
			return jedis.zrange(key, start, end);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return null;
	}
	
	/**
	 * 获取指定元素分值
	 * @param key
	 * @param member
	 * @return
	 */
	public double zscore(String key,String member){
		Jedis jedis = getDbClient();
		try {
			if(null != jedis.zscore(key, member)){
				return jedis.zscore(key, member);
			}else{
				return -1;
			}
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 获取倒序排名
	 * @param key
	 * @param member
	 * @return
	 */
	public long zrevrank(String key,String member){
		
		Jedis jedis = getDbClient();
		try {
			if(null != jedis.zrevrank(key, member)){
				return jedis.zrevrank(key, member);
			}else{
				return -1;
			}
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
		return -1;
	}
	
	/**
	 * 移除sorted set当中的元素
	 * @param key
	 * @param value
	 */
	public void zrem(String key, String value) {
		Jedis jedis = getDbClient();
		try {
			jedis.zrem(key, new String[]{value});
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
		} finally {
			release(jedis);
		}
	}
	
	/**
	 * 删除指定键
	 * @param key
	 */
	public void del(String key){
		Jedis jedis = getDbClient();
		try {
			jedis.del(key);
		} catch(Exception e) {
			log.error("数据库查询异常", e);
			returnBroken(jedis);
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
