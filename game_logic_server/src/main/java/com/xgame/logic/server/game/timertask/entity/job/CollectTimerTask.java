package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.CollectTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.entity.WorldMarch;

import lombok.extern.slf4j.Slf4j;

/**
 * 采集任务
 * @author jacky.jiang
 *
 */
@Slf4j
public class CollectTimerTask extends AbstractTimerTask{

	public CollectTimerTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		
//		CollectTimerTaskData collectTimerTaskData = new CollectTimerTaskData();
//		collectTimerTaskData.setMarchId((Long)params[0]);
//		
//		timerTaskData.setParam(collectTimerTaskData);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		CollectTimerTaskData collectTimerTaskData = (CollectTimerTaskData)data.getParam();
		if(collectTimerTaskData != null) {
			if(collectTimerTaskData.getMarchId() > 0) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, collectTimerTaskData.getMarchId());
				if(worldMarch != null) {
					log.info("采集完成,采集队列id:[{}]", collectTimerTaskData.getMarchId());
					worldMarch.setExlporerTaskId(0);
					InjectorUtil.getInjector().dbCacheService.update(worldMarch);
					
					worldMarch.executor.handleReturn();
				} else {
					log.info("采集队列不存在,采集队列id:[{}]", collectTimerTaskData.getMarchId());
				}
			}
		}
		return super.onExecute(player, data);
	}

}
