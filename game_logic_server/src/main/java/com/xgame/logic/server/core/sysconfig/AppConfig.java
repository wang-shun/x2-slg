package com.xgame.logic.server.core.sysconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.message.MessageSystem;
import com.xgame.config.ConfigSystem;
import com.xgame.framework.config.ConfigLoader;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.framework.schedule.listener.JobRunTimeListener;
import com.xgame.framework.schedule.manager.JobRunTimeSystem;
import com.xgame.framework.schedule.manager.ScheduleSystem;
import com.xgame.logic.server.LogicBootstrap;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.game.country.CountryManager;
import com.xgame.logic.server.game.player.PlayerManager;

@Configuration()
@ImportResource("classpath:/xgame/appStart.xml")
@Import({ ConfigLoader.class, EventBus.class, MessageSystem.class, PlayerManager.class, CommandProcessor.class,
		CountryManager.class, ScheduleSystem.class, JobRunTimeSystem.class, JobRunTimeListener.class, ConfigSystem.class })
@ComponentScan(basePackageClasses = LogicBootstrap.class)
public class AppConfig {

}
