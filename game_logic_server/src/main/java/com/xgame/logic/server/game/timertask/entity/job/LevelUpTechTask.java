package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.TechTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 科技升级队列
 * @author jacky.jiang
 *
 */
public class LevelUpTechTask extends AbstractTimerTask {

	public LevelUpTechTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData stateTaskData = new TimerTaskData();
//		TechTimerTaskData techTimerTaskData = new TechTimerTaskData();
//		techTimerTaskData.setSid((int)params[0]);
//		techTimerTaskData.setState((int)params[1]);
//		techTimerTaskData.setUid((int)params[2]);
//		stateTaskData.setParam(techTimerTaskData);
//		return stateTaskData;
//	}
	
	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		TechTimerTaskData techTimerTaskData = (TechTimerTaskData)data.getParam();
		player.getCountryManager().getTechBuildControl().levelSuccess(player, techTimerTaskData.getSid());
		InjectorUtil.getInjector().dbCacheService.update(player);
		return super.onExecute(player, data);
	}

	@Override
	public boolean cancelTask(Player player, TimerTaskData data) {
		TechTimerTaskData techTimerTaskData = (TechTimerTaskData)data.getParam();
		player.getCountryManager().getTechBuildControl().cancelTechUp(player, techTimerTaskData.getSid());
		return super.cancelTask(player, data);
	}
	
	public int maxQueueCapacity(Player player) {
		return PlayerAttributeManager.get().techQueueNum(player.getId());
	}
	
}
