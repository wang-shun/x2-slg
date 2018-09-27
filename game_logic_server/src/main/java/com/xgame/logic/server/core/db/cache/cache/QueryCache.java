/**
 * 
 */
package com.xgame.logic.server.core.db.cache.cache;



/**
 * 查询缓存接口
 * @author jiangxt
 *
 */
public interface QueryCache  {
	
	/**
	 * 根据查询key获取查询名
	 * @param queryKey
	 * @return
	 */
	Object get(String key);
	
	/**
	 * 返回当期缓存的大小
	 * @return
	 */
	int size();
	
	/**
	 * 清掉缓存
	 */
	void clear();
	
	/**
	 * 放入一个缓存结果
	 * @param queryName
	 * @param queryResult
	 * @return
	 */
	Object put(String key, Object queryResult);
	
	/**
	 * 放入一个缓存结果(如果缓存key已经缓存了结果就不加入缓存)
	 * @param queryName
	 * @param queryResult
	 * @return
	 */
	Object putIfAbsent(String key, Object queryResult);
}
