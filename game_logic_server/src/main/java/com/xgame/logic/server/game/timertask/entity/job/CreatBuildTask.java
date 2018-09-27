package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 建造任务队列	
 * @author jacky.jiang
 *
 */
public class CreatBuildTask extends AbstractTimerTask {
	
	// 创建建筑
	public static final int CREATE_BUILD_CMD = 1;
	
	// 升级建筑
	public static final int LEVEL_UP_BUILD_CMD = 2;
	
	public CreatBuildTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

	@Override
	public TimerTaskData factoryTimerTaskData(Object param) {
		TimerTaskData data = new TimerTaskData();
		data.setParam(param);
		return data;
	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		BuildTimerTaskData buildTimerTaskData = (BuildTimerTaskData)data.getParam();
		switch (data.geteType()) {
			case CREATE_BUILD_CMD:
				player.getCountryManager().timeUp(buildTimerTaskData.getSid(), buildTimerTaskData.getBuildingUid());
				break;
			case LEVEL_UP_BUILD_CMD:
				player.getCountryManager().buildLevelTimeEnd(data);
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public TimerTaskCommand getTimerTaskCommand() {
		return TimerTaskCommand.BUILD;
	}

	@Override
	public boolean cancelTask(Player player, TimerTaskData data) {
		BuildTimerTaskData buildTimerTaskData = (BuildTimerTaskData)data.getParam();
		BuildControl buildControl = player.getCountryManager().getBuildControls().get(buildTimerTaskData.getSid());
		if (buildControl != null) {
			buildControl.cancelTimerTask(player, buildTimerTaskData.getBuildingUid());
		}
		return super.cancelTask(player, data);
	}

	@Override
	public int maxQueueCapacity(Player player) {
		return PlayerAttributeManager.get().buildingQueueNum(player.getId());
	}
	
}
