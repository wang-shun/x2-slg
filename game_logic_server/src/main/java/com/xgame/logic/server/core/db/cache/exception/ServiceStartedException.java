/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;

/**
 * @author fasnth
 *
 */
public class ServiceStartedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5320094108391653113L;
	
	public ServiceStartedException(Object bean){
		super(String.format("服务 [%s]@[%s]已经启动中!", bean.getClass(), bean.toString()));
	}
}
