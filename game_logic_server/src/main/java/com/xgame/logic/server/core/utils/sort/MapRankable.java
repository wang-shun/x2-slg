/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于map的排序
 * @author jacky
 *
 */
public class MapRankable{
	
	/**属主id*/
	public static final String OWNER_ID = "ownerId";
	/**排行名次*/
	public static final String RANK = "rank";
	/**排行数值*/
	private final Map<String, Object> context = new ConcurrentHashMap<String, Object>();

	public static MapRankable valueOf(Map<String, Object> value) {
		MapRankable mapRankable = new MapRankable();
		mapRankable.replaceContext(value);
		return mapRankable;
	}
	
	public MapRankable() {
		super();
	}
	
	/**
	 * 设置键值
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value){
		this.context.put(key, value);
	}
	
	/**
	 * 获取键值
	 * @param key
	 * @return
	 */
	public Object getValue(String key){
		return this.context.get(key);
	}
	
	/**
	 * 在现有的值上增加指定的值
	 * @param key
	 * @param increment
	 */
	public void increase(String key, double increment){
		Number value = (Number)this.getValue(key);
		if(value == null){
			value = 0;
		}
		
		value = value.doubleValue() + increment;
		this.setValue(key, value);
	}
	
	/**
	 * 在现有的值上减少指定的值
	 * @param key
	 * @param increment
	 */
	public void decrease(String key, double increment){
		this.increase(key, -increment);
	}
	
	/**
	 * 将指定的map替换到当前的排序对象中
	 * @param value
	 */
	public void replaceContext(Map<String, Object> value){
		if(value != null){
			this.context.putAll(value);
		}
	}

	/**
	 * 将当前的排序信息拷贝到指定的排序对对象上(名次除外)
	 * @param ranking 正在排行榜中的排名对象()
	 */
	public MapRankable merge(MapRankable ranking) {
		Integer rank = (Integer)ranking.context.get(RANK);
		MapRankable clone = clone();
		if(rank != null){
			clone.context.put(RANK, rank);
		}
		return clone;
	}
	
	/**
	 * 克隆一个排序对象(返回副本) 
	 */
	@Override
	public MapRankable clone(){
		MapRankable clone = new MapRankable();
		clone.replaceContext(this.context);
		return clone;
	}

	/**
	 * 获取排名
	 * @return
	 */
	public int getRank() {
		return ((Number)context.get(RANK)).intValue();
	}

	/**
	 * 设置排名
	 * @param rank
	 */
	public void setRank(int rank) {
		context.put(RANK, rank);
	}
	
	/**
	 * 获取宿主id(默认以 ownerId作为key)
	 * @return
	 */
	public Object getOwnerId() {
		return context.get(OWNER_ID);
	}

	/**
	 *  获取宿主id(默认以 ownerId作为key)
	 * @return
	 */
	public long getOwnerIdAsLong() {
		return ((Number)context.get(OWNER_ID)).longValue();
	}
	
	/**
	 * 获取宿主id(默认以 ownerId作为key)
	 * @return
	 */
	public int getOwnerIdAsInt() {
		return ((Number)context.get(OWNER_ID)).intValue();
	}
	
	/**
	 * 获取宿主id(默认以 ownerId作为key)
	 * @return
	 */
	public String getOwnerIdAsString() {
		return (String)context.get(OWNER_ID);
	}
	
	/**
	 * 设置宿主id(默认以 ownerId作为key)
	 * @param ownerId
	 */
	public void setOwnerId(Object ownerId) {
		context.put(OWNER_ID, ownerId);
	}
	
	public int getInt(String key){
		return ((Number)context.get(key)).intValue();
	}
	public double getDouble(String key){
		return ((Number)context.get(key)).doubleValue();
	}
	public long getLong(String key){
		return ((Number)context.get(key)).longValue();
	}
	public boolean getBoolean(String key){
		return (Boolean)context.get(key);
	}
	public Date getDate(String key){
		return (Date)context.get(key);
	}
	public String getString(String key){
		return (String)context.get(key);
	}
	

	/**
	 * 转成map(返回对象就是排序对象的引用而不是副本)
	 * @return
	 */
	public Map<String, Object> toMap(){
		return this.context;
	}
	
}
