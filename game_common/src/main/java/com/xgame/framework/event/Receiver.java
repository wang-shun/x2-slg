package com.xgame.framework.event;



/**
 * 事件接收器
 * @author jacky.jiang
 *
 */
public interface Receiver<T extends Event> {

	/**
	 * 接收事件处理
	 * @param t
	 */
	public void receive(T t);
}
