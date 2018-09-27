/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;


/**
 * @author jiangxt
 *
 */
public class BuffNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000199560591066632L;

	public BuffNotExistException(Class<?> entityClass){
		super(String.format("实体类 [{0}] buffer队列不存在!", entityClass.getName()));
	}
	
	
}
