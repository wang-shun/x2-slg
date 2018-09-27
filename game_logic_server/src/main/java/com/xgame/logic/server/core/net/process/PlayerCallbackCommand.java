package com.xgame.logic.server.core.net.process;

import com.xgame.msglib.XMessage;


/**
 *  带有callback的返回
 * @author jacky.jiang
 *
 */
public abstract class PlayerCallbackCommand<E extends XMessage>  extends PlayerCommand<E> {

	@Override
	public boolean isCallback() {
		return true;
	}
	
	@Override
	protected void action() {
		XMessage message = callback();
		message.callbackId = this.message.getCallbackId();
		this.getPlayer().send(message);
	}

	/**
	 * 回调执行方法
	 * @return
	 */
	protected abstract XMessage callback();

}
