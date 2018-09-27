/**
 * 
 */
package com.xgame.logic.server.core.db.cache.buffer;

/**
 * 对象在缓冲中的状态
 * @author jiangxt
 *
 */
public enum BufferStatus {
	
	/**
	 * 不在缓冲托管中
	 */
	TRANSIENT,
	
	/**
	 * 等待更新中
	 */
	WAITING_UPDATE,
	
	/**
	 * 等待删除中
	 */
	WAITING_DELETE;

}
