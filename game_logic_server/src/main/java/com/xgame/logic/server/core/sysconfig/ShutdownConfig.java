package com.xgame.logic.server.core.sysconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.xgame.framework.config.ConfigLoader;

/**
 * 关服配置
 * @author jacky.jiang
 *
 */
@Configuration()
@ImportResource("classpath:/xgame/system_rmi.xml")
@Import({ConfigLoader.class})
public class ShutdownConfig {

}
