/**
 * 
 */
package com.xgame.logic.server.core.db.cache.entity;

import java.io.Serializable;

/**
 * 可锁对象
 * @author jiangxt
 *
 */
public interface Lockable<T extends Serializable> {

	/**
	 * 获取实体标识
	 * @return
	 */
	T getIdentity();
	
}
