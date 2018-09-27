package com.xgame.logic.server.game.timertask.entity.job;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.RunCampTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.entity.WorldMarch;

/**
 * 行军队列
 * @author jacky.jiang
 *
 */
@Slf4j
public class RunCampTask extends AbstractTimerTask {
	
	public RunCampTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

	@Override
	public boolean onAdd(Player player, TimerTaskData data) {
		return true;
	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		
		if(data == null) {
			log.error("行军队列数据为空。");
			return false;
		}
		
		RunCampTaskData runCampTaskData = (RunCampTaskData) data.getParam();
		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, runCampTaskData.getWorldMarchId());
		if (worldMarch == null) {
			InjectorUtil.getInjector().dbCacheService.delete(data);
		}
		
		Future<Boolean> future = InjectorUtil.getInjector().processor.getMapExecutor().submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				try {
					if(worldMarch == null) {
						log.error("行军队列数据为空。");
						return false;
					}
					
					// 行军信息
					if(worldMarch.getMarchState() == MarchState.MARCH) {
						worldMarch.executor.toDestination();
					} else if(worldMarch.getMarchState() == MarchState.OCCUPY) {
						worldMarch.executor.handleReturn();
					} else if(worldMarch.getMarchState() == MarchState.BACK) {
						worldMarch.executor.backReturnHome();
					}
 					return true;
				} catch(Exception e) {
					worldMarch.executor.failReturn();
					log.error("行军队列处理异常：", e);
					return false;
				}
			}
		});
		
		try {
			return future.get();
		} catch (Exception e) {
			log.error("行军队列返回异常：", e);
			return false;
		} 
	}
	
	@Override
	public void onException(Player player, TimerTaskData data) {
		RunCampTaskData runCampTaskData = (RunCampTaskData) data.getParam();
		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, runCampTaskData.getWorldMarchId());
		if(worldMarch != null) {
			worldMarch.executor.failReturn();
		}
		super.onException(player, data);
	}

	@Override
	public boolean speedUp(Player player, TimerTaskData data, int time) {
		return super.speedUp(player, data, time);
	}

	@Override
	public boolean onRemove(Player player, TimerTaskData data) {
		InjectorUtil.getInjector().dbCacheService.delete(data);
		return super.onRemove(player, data);
	}

	@Override
	public int maxQueueCapacity(Player player) {
		return 1;
	}
}
