package com.xgame.data.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import com.xgame.data.server.config.AppConfig;
import com.xgame.framework.config.ConfigDataSourceLoader;
import com.xgame.framework.config.LogbackConfigurer;

@Slf4j
public class DataServerBootstrap {
	private static AnnotationConfigApplicationContext context;
	public static Environment env;
	static{
		System.setProperty("-Dprotostuff.runtime.morph_non_final_pojos", "true");
	}
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					String[] _args = args[i].split("=");
					System.setProperty(_args[0], _args[1]);
					log.debug("add args : {} = {}", _args[0], _args[1]);
				}
			}

			//FIXME 加到java启动的classpath里
//			System.setProperty("-Dprotostuff.runtime.morph_non_final_pojos", "true");
			LogbackConfigurer.init();
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
			ctx.getEnvironment()
					.getPropertySources()
					.addLast(
							ConfigDataSourceLoader.of("data-server", true)
									.load(ctx));
			ctx.register(AppConfig.class);
			init(ctx);
		} catch (Exception ex) {
			close(ex);
		}
	}
	
	private static void init(AnnotationConfigApplicationContext ctx)
			throws Exception {
		env = ctx.getEnvironment();
        
		ctx.registerShutdownHook();

		ctx.refresh();

		context = ctx;

		DataServer server = getBean(DataServer.class);

		server.startRpcServer();
		
	

		log.debug(".......................DataServer bootstrap success......................");

	}

	public static void close(Throwable cause) {
		if (cause != null) {
			log.error("shutdown data server when :", cause);
		} else {
			log.warn("showdown data server at {}", (Object) Thread
					.currentThread().getStackTrace());
		}
		if (context != null) {
			context.close();
		}
	}

	private static <T> T getBean(Class<T> classType) {
		return context.getBean(classType);
	}
}
