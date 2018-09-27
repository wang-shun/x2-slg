/**
 * 
 */
package com.xgame.logic.server.core.db.cache.exception;


/**
 * @author jiangxt
 *
 */
public class MultiEvictionListenerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000199560591066632L;

	public MultiEvictionListenerException(){
		super("注册多个缓存溢出监听器");
	}
}
