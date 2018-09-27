package com.xgame.logic.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xgame.framework.config.ConfigDataSourceLoader;
import com.xgame.logic.server.core.sysconfig.ShutdownConfig;
import com.xgame.logic.server.game.cross.rmi.SystemRmiService;


/**
 * 服务器关闭
 * @author jacky.jiang
 *
 */
public class LogicShutdown {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("system_rmi");
		ctx.getEnvironment().getPropertySources().addLast(ConfigDataSourceLoader.of("logic-server", true).load(ctx));
		ctx.register(ShutdownConfig.class);
		ctx.refresh();
		
		SystemRmiService systemRmiService = (SystemRmiService)ctx.getBean("systemRmiClient");
		systemRmiService.shutdown();
	}
}
