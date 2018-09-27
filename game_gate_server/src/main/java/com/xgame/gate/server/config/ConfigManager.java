package com.xgame.gate.server.config;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 配置文件初始化
 * @author jacky.jiang
 *
 */
public class ConfigManager {

	public static final String CONFIG_PATH = "xgame"+File.separator+"application.conf";
    private Config config;
    private static final ConfigManager configInit = new ConfigManager();

    //加载配置文件
    public void loadConfig() {
        // 加载公共配置文件,默认加载classpath下的application.conf,application.json和application.properties文件。通过ConfigFactory.load()加载。
    	config = ConfigFactory.load(CONFIG_PATH);
    }

    public Config getConfig() {
        return config;
    }

    public static ConfigManager getInstance() {
        return configInit;
    }
    
    public int getC2GPort() {
    	return ConfigManager.getInstance().getConfig().getInt("realm.c2gport");
    }
    
    public int getS2GPort() {
    	return ConfigManager.getInstance().getConfig().getInt("realm.s2gport");
    }
    
}
