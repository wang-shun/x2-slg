/**
 * 
 */
package com.xgame.logic.server.core.db.cache;

import java.io.Serializable;

import com.xgame.logic.server.core.db.cache.entity.IEntity;

/**
 * 数据访问接口
 * @author jiangxt
 *
 */
public interface DbCacheService{

	/**
	 * 根据主键获取对象
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T extends IEntity<? extends Serializable>> T get(Class<T> clazz, Serializable id);
	
	/**
	 * 创建并保存一个实体对象
	 * @param <T>
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	<T extends IEntity> T create(T entity);
	
	/**
	 * 更新一个实体对象
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends IEntity<? extends Serializable>> void update(T entity);
	
	/**
	 *删除一个实体对象
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends IEntity<? extends Serializable>> void delete(T entity);
	
	
//	/**
//	 * 获取实体对象如果不存在就创建一个
//	 * @param <T>
//	 * @param clazz
//	 * @param id
//	 * @param entityBuilder
//	 * @return
//	 */
//	<T extends IEntity<? extends Serializable>> T getOrCreate(Class<T> clazz, Serializable id, EntityBuilder<T> entityBuilder);
	
	/**
	 * 将buffer中的数据倾倒入库
	 */
	void dumpBuffer();
}
