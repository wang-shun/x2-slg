package com.xgame.logic.server.gm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.xgame.logic.server.game.player.entity.Player;

/**
 *动态代理处理者  该类实现动态代理的权限管理 
 *2016-8-23  14:15:33
 *@author ye.yuan
 *
 */
public class ProxyInvocationHandler implements InvocationHandler {
	
	private GMProxySystem proxySystem;

	public ProxyInvocationHandler(GMProxySystem proxySystem) {
		this.proxySystem=proxySystem;
	}
	
	@Override
	public Object invoke(Object proxy, final Method method, final Object[] args)throws Throwable {
		Player player = null;
		if (args != null && args.length > 0 && args[0] instanceof Player) {
			player = (Player)args[0];
		}
		//权限控制
		if(!playerBeforeAction(player, method)) {
			return null;
		}
		if (method.getReturnType() == void.class) {
			
		} 
		return method.invoke(proxySystem, args);
	}
	
	/**
	 * 调用者执行前判断是否有资格执行
	 * @param player
	 * @param method
	 * @return
	 */
	private boolean playerBeforeAction(Player player, Method method) {
		/*
		 * 根据注解 Admin 的等级  查看帐号是否有资格操作gm
		 */
		
		return true;
	}
}
