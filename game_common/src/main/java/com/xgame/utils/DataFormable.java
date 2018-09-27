package com.xgame.utils;


/**
 *标准初始化manager接口
 *2016-7-18  08:57:18
 *@author ye.yuan
 *
 */
public interface DataFormable {
	
	/*
	 * 先调用dbfrom进行数据库数据初始化
	 * 最后init初始化管理器
	 */
	
	/**
	 * 数据库拿到数据后 功能内的一系列操作
	 * @param player
	 * @param dbData
	 */
	void dbRead(Object ... dbData);
	

	Object dbWrite(Object ... dbData);
}
