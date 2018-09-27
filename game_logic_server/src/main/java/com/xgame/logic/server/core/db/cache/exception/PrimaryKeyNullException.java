/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;


/**
 * @author jiangxt
 *
 */
public class PrimaryKeyNullException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000199560591066632L;

	public PrimaryKeyNullException(Class<?> entityClass){
		super(String.format("实体类 [%s] 持久化时主键为空!", entityClass.getName()));
	}
	
	
}
