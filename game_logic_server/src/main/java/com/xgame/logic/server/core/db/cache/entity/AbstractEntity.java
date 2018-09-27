package com.xgame.logic.server.core.db.cache.entity;

import java.io.Serializable;

/**
 * 抽象实体
 * @author jiangxt
 * @param <K>
 */
public abstract class AbstractEntity<K extends Serializable & Comparable<K>> extends EntityAdapter<K> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3647010568531434899L;
	
//	/**
//	 * 保存数据
//	 * @param jbData
//	 */
//	public abstract void toJBaseData(Map<String, Object> jbaseData);
//	
//	/**
//	 * 添加数据
//	 * @param jbaseData
//	 */
//	public abstract void fromJBaseData(Map<String, Object> jbaseData);

}
