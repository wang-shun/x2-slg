package com.xgame.logic.server.core.gamelog.event;

/**
 * 关注的事件列表
 * @author jacky.jiang
 *
 */
public interface IListener {
	
	/**
	 * 初始化动作
	 */
	public void init();
	
	/**
	 * 关注的事件列表
	 * @return
	 */
	public int[] focusActions();
	
	/**
	 * 事件处理
	 * @param e
	 */
	public void onAction(IEventObject e);
}
