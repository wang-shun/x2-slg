/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

import java.io.Serializable;

import com.xgame.logic.server.core.db.cache.exception.UpdateOnDeleteEntityExeption;

/**
 * 待持久化的数据缓冲池
 * @author jacky
 *
 */
public interface Buffer<T> extends Serializable {

	/**
	 * 获取实体对象的缓冲状态
	 * @param id
	 * @return
	 */
	BufferStatus guess(Serializable id);
	
	/**
	 * 插入新对象
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 异步延迟更新
	 * @param entity
	 * @throws UpdateOnDeleteEntityExeption
	 */
	void update(T entity) throws UpdateOnDeleteEntityExeption;
	
	/**
	 * 异步即时更新
	 * @param entity
	 * @throws UpdateOnDeleteEntityExeption
	 */
	void updateImmediately(T entity) throws UpdateOnDeleteEntityExeption;
	
	/**
	 * 删除对象
	 * @param entity
	 */
	void delete(T entity);
	
	/**
	 * 将数据倾倒入库
	 * @return 倾倒的对象数量
	 */
	int dump();
}
