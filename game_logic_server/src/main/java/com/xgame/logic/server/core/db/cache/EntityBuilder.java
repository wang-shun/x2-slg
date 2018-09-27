/**
 * 
 */
package com.xgame.logic.server.core.db.cache;

import java.io.Serializable;

import com.xgame.logic.server.core.db.cache.entity.IEntity;

/**
 * 实体构建起
 * @author jiangxt
 *
 */
public interface EntityBuilder<T extends IEntity<? extends Serializable>> {

	/**
	 * 构建一个实体对象
	 * @return
	 */
	T build();
}
