package com.message;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.io.MSerializer;

import io.netty.buffer.ByteBuf;

/**
 *消息节点对象
 *2016-11-25  14:48:18
 *@author ye.yuan
 *
 */
@SuppressWarnings("unchecked")
public class MessageNode<E extends Communicationable> {

	/**
	 * 处理类和消息类
	 */
	private Class<?> handler,message;
	
	/**
	 * 生产一个处理器
	 * @return
	 */
	public PlayerCommand<? extends Communicationable> newHandler(){
		return (PlayerCommand<? extends Communicationable>)InjectorUtil.getInjector().getApplicationContext().getBean(handler);
	}
	
	/**
	 * 给我收到的客户端byte数组  得到一套序列化好的handler类
	 * @param bytes
	 * @return
	 */
	public PlayerCommand<? extends Communicationable> gen(byte [] bytes){
		PlayerCommand<? extends Communicationable> newHandler = newHandler();
		newHandler.setMessage((Communicationable)MSerializer.decode(bytes, message));
		return newHandler;
	}
	
	/**
	 * 给我buff也行
	 * @param buff
	 * @return
	 */
	public PlayerCommand<? extends Communicationable> gen(ByteBuf buff){
		byte[] buffer = new byte[buff.readableBytes()];
		buff.readBytes(buffer);
		return gen(buffer);
	}

	public MessageNode(Class<?> handler, Class<?> message) {
		this.handler = handler;
		this.message = message;
	}

	public Class<?> getHandler() {
		return handler;
	}

	public void setHandler(Class<?> handler) {
		this.handler = handler;
	}

	public Class<?> getMessage() {
		return message;
	}

	public void setMessage(Class<?> message) {
		this.message = message;
	}
	
}
