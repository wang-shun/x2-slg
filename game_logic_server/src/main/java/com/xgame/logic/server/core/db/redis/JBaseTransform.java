package com.xgame.logic.server.core.db.redis;

/**
 * jaseData转换器
 * @author jacky.jiang
 *
 */
public interface JBaseTransform {
	
	/**
	 * 将java转换城jbaseData
	 * @return
	 */
	public JBaseData toJBaseData();
	
	/**
	 * 将jbaseData转换城java对象
	 * @param jBaseData
	 */
	public void fromJBaseData(JBaseData jBaseData);
}
