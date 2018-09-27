/**
 * 
 */
package com.xgame.logic.server.core.db.cache.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 实体接口
 * @author jiangxt
 *
 */
public interface IEntity<K extends Serializable> extends JBaseTransform {

	/**
	 * 获取id
	 * @return
	 */
	K getId();
	
	/**
	 * 设置主键值
	 * @param k
	 */
	void setId(K k);
}
