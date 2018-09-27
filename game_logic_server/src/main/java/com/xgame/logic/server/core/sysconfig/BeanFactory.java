package com.xgame.logic.server.core.sysconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xgame.framework.executer.MapExecutor;
import com.xgame.framework.executer.ParallelExecutor;
import com.xgame.framework.executer.SyncExecutor;

@Configuration
public class BeanFactory {

	@Value("${xgame.logic.server.session.max}")
	private int sessionCapacity;

	@Bean
	public SyncExecutor getLogicExecuter() {
		return new SyncExecutor("logic-executer");
	}

	@Bean
	public ParallelExecutor getParallelExecuter() {
		return new ParallelExecutor("parallel-executer", 8);
	}
	
	
	@Bean
	public MapExecutor getMapExecutor() {
		return new MapExecutor("map-executor");
	}

}
