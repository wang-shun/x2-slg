/**
 * 
 */
package com.xgame.logic.server.core.utils.sort;

/**
 * 过滤器
 * @author jacky
 *
 */
public interface Filter<T> {
	
	/**
	 * 接收条件
	 * @return true-条件满足
	 */
	boolean accept(T object);

}
