package com.xgame.logic.server.core.system;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgame.framework.network.server.ServerStateEnum;
import com.xgame.logic.server.core.db.cache.factory.DbCacheFactoryBean;
import com.xgame.logic.server.core.net.gate.MultiGateManager;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.scheduler.SchedulerFactoryBean;
import com.xgame.logic.server.game.player.PlayerManager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogicServer {

	@Getter
	private static ServerStateEnum state;

	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private MultiGateManager multiGateManager;

	@Value("${xgame.logic.server.id}")
	private int serverKey;
	@Value("${xgame.logic.server.group}")
	private int group;
	@Value("${xgame.logic.dumpIntervals}")
	private int dumpIntervals;

	public boolean init() {

		log.info("logic server bootstrap");

		state = ServerStateEnum.NOT_START;
		DbCacheFactoryBean dbCacheFactoryBean = InjectorUtil.getInjector().getBean(DbCacheFactoryBean.class);
		dbCacheFactoryBean.init();
		
		SchedulerFactoryBean schedulerFactoryBean = InjectorUtil.getInjector().getBean(SchedulerFactoryBean.class);
		schedulerFactoryBean.init();
		
		if (!playerManager.init()) {
			log.error("system init : playerManager error exit!");
			return false;
		}
		
		// 连接到gate
		multiGateManager.init();
		
		// 启服生命周期处理
		LifeCycleManager lifeCycleManager = InjectorUtil.getInjector().getBean(LifeCycleManager.class);
		lifeCycleManager.start();
	
		log.info("logic server init success.");
		return true;
	}
	
	/**
	 * 服务器关闭
	 */
	@PreDestroy
	public void shutdown() {
		log.info(">>>>>>>> logic server is shutting down...");

		state = ServerStateEnum.END;

		log.info("关闭连接");
		multiGateManager.shutdown();
		
		
//		LifeCycleManager lifeCycleManager = InjectorUtil.getInjector().getBean(LifeCycleManager.class);
//		lifeCycleManager.stop();
		
		// add more clean up work here
		log.info("logic server shut down complete");
	}

	
	public void checkAfterInit() {
		log.info("logic server bootstrap complete");
	}

	/**
	 * 服务器启动
	 */
	public void start() {
		state = ServerStateEnum.RUNNING;
		log.info("logic server is running ");
	}

}
