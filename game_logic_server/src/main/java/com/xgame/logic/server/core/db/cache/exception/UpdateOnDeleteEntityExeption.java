/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;

import com.xgame.logic.server.core.db.cache.entity.IEntity;

/**
 * 更新删除实体异常
 * @author jiangxt
 *
 */
public class UpdateOnDeleteEntityExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4972211633059028494L;

	public UpdateOnDeleteEntityExeption(IEntity<?> entity) {
		super(String.format("实体类 [%s] id : [%s] 在已删除的对象上做更新操作!", entity.getClass().getName(), entity.getId()));
	}
	
}
