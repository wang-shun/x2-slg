package com.xgame.gate.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;

import com.xgame.gate.server.akka.AkkaSystemFactory;
import com.xgame.gate.server.cache.GateChannelCache;
import com.xgame.gate.server.config.ConfigManager;


/**
 * 网管服务器启动入口
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateBootstrap {

	public static Environment env;
	
    public static void main(String[] args) {
        try {
        	if (args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					String[] _args = args[i].split("=");
					System.setProperty(_args[0], _args[1]);
					log.debug("add args : {} = {}", _args[0], _args[1]);
				}
			}
			
        	//　加载配置文件
        	ConfigManager.getInstance().loadConfig();
			// 初始化akka
			AkkaSystemFactory.getInstance().start();
			// 开启网络连接
			GateAgentServer.getInstance().init();
			// 初始客户端连接
			GateChannelCache.getInstance().initAkka();
        } catch (Exception e) {
            e.printStackTrace();
            close(e.getCause());
        }
    }
    
	public static void close(Throwable cause) {
		if (cause != null) {
			log.error("shutdown world server when :", cause);
		} else {
			log.warn("showdown world server at {}", (Object) Thread .currentThread().getStackTrace());
		}
		
		// 关闭网络连接
		GateAgentServer.getInstance().shutdown();
	}

}
