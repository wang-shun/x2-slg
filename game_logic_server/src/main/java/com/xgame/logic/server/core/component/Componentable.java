package com.xgame.logic.server.core.component;



/**
 *组件接口
 *这套体系基于玩家孕育而生,但扩展为其他对象也可使用
 *每个玩家下面的管理器都是自身的组件,应该被统一管理销毁等
 *2016-9-29  09:52:05
 *@author ye.yuan
 *
 */
public interface Componentable<E> {

	
	/**
	 * 新角色第一次进入游戏
	 * @param param
	 */
	void firstLoad(Object ... param);
	
	/**
	 * 每次对象初始化的时候
	 * @param param
	 */
	void loadComponent(Object ... param);

	/**
	 * 每次登录后
	 * @param param
	 */
	void loginLoad(Object ... param);
	
	/**
	 * 获得主体,也就是player
	 * @return
	 */
	E getPlayer();
	
	/**
	 * 设置主体
	 * @param product
	 */
	void setProduct(E product);
	
	/**
	 * 统一销毁
	 */
	void destroy();
}
