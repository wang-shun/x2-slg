package com.xgame.logic.server.game.cross.rmi;


/**
 * 系统rmi入口
 * @author jacky.jiang
 *
 */
public interface SystemRmiService {
	
	/**
	 * 跨服请求
	 */
	public void shutdown();
	
	/**
	 * 刷新配置
	 */
	public void refreshConfig();
	
	/**
	 * 单元测试
	 */
	public void unionTest();
	
	/**
	 * 
	 */
	public void exportWorldPoint();
}
