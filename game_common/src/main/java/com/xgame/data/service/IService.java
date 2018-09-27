package com.xgame.data.service;

/**
 * 服务接口
 * @author jacky.jiang
 *
 */
public interface IService {
	
	/**
	 * 启动服务
	 */
    public void init();

    /**
     * 关闭服务
     */
    public void shutdown();
}
