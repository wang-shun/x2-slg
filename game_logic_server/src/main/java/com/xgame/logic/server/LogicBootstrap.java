package com.xgame.logic.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import com.xgame.framework.config.ConfigDataSourceLoader;
import com.xgame.framework.config.LogbackConfigurer;
import com.xgame.logic.server.core.sysconfig.AppConfig;
import com.xgame.logic.server.core.system.LogicServer;

@Slf4j
public class LogicBootstrap {
	private static AnnotationConfigApplicationContext context;
	public static Environment env;
	public static void main(String ... args) {
		try {
			if (args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					String[] _args = args[i].split("=");
					System.setProperty(_args[0], _args[1]);
					log.debug("add args : {} = {}", _args[0], _args[1]);
				}
			}
			LogbackConfigurer.init();
			initSpring();
			init();
		} catch (Exception ex) {
			ex.printStackTrace();
			close(ex);
		}
	}
	
 
	public static void initSpring() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("appStart");
		ctx.getEnvironment().getPropertySources().addLast(ConfigDataSourceLoader.of("logic-server", true).load(ctx));
		ctx.getEnvironment().getPropertySources().addLast(ConfigDataSourceLoader.of("log-config", true).load(ctx));
		ctx.getEnvironment().getPropertySources().addLast(ConfigDataSourceLoader.of("cross-redis", true).load(ctx));
		ctx.register(AppConfig.class);
		env = ctx.getEnvironment();
		ctx.registerShutdownHook();
		ctx.refresh();
		context = ctx;
	}

	private static void init() throws Exception {
		LogicServer server = getBean(LogicServer.class);
		if (!server.init()) {
			log.error("logicserver init error exit!");
			close();
		}
		server.checkAfterInit();
		server.start();
	
		log.debug(" config in env : xgame.logic.server.session.max = {}", env.getProperty("xgame.logic.server.session.max"));
		log.debug(".......................LogicServer bootstrap success......................");
	}

	public static void close() {
		close(null);
	}

	public static void close(Throwable cause) {
		
		if (cause != null) {
			log.error("shutdown logic server when :", cause);
		} else {
			log.warn("showdown logic server at {}", (Object) Thread.currentThread().getStackTrace());
		}
		
		// 游戏当中的加载项(关闭游戏当中的选项)
		if (context != null) {
			context.close();
		}
	}

	private static <T> T getBean(Class<T> classType) {
		return context.getBean(classType);
	}
}
