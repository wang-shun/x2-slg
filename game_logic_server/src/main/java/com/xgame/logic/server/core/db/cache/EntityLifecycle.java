/**
 * 
 */
package com.xgame.logic.server.core.db.cache;

/**
 * 实体生命周期
 * @author jiangxt
 *
 */
public interface EntityLifecycle {

	/**
	 * 在加载后放入缓存前调用
	 */
	void onLoad();
	
	/**
	 * 在执行数据库update操作之前调用
	 */
	void onUpdate();
	
	/**
	 * 在执行数据库update操作之后调用
	 */
	void postUpdate();
	
	/**
	 * 在执行数据库delete操作之前调用
	 */
	void onDelete();
	
	/**
	 * 在执行数据库save操作之前调用
	 */
	void onSave();
	
}
