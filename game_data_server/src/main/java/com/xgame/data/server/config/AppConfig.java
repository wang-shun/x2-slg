package com.xgame.data.server.config;

 
 
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xgame.data.server.DataServer;
import com.xgame.data.server.DataServerServiceImpl;
import com.xgame.framework.config.ConfigLoader;

@Configuration
@Import({DataServerServiceImpl.class , ConfigLoader.class })
@ComponentScan(basePackageClasses = DataServer.class)
public class AppConfig {
}
