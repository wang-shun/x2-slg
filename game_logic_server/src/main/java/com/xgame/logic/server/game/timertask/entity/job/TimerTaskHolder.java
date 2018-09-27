package com.xgame.logic.server.game.timertask.entity.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;


/**
 * timerTask缓存
 * @author jacky.jiang
 *
 */
public class TimerTaskHolder {
	
	private static Map<TimerTaskCommand, ITimerTask<?>> timerTaskMap = new ConcurrentHashMap<>();
	
	static {
		timerTaskMap.put(TimerTaskCommand.BUILD, (ITimerTask<?>) new CreatBuildTask(TimerTaskCommand.BUILD));
		
		timerTaskMap.put(TimerTaskCommand.SOLDIER_CAR_OUTPUT, new CampOutputTask(TimerTaskCommand.SOLDIER_CAR_OUTPUT));
		timerTaskMap.put(TimerTaskCommand.SOLDIER_PLANT_OUTPUT, new CampOutputTask(TimerTaskCommand.SOLDIER_PLANT_OUTPUT));
		timerTaskMap.put(TimerTaskCommand.SOLDIER_TANK_OUTPUT, new CampOutputTask(TimerTaskCommand.SOLDIER_TANK_OUTPUT));
		
		timerTaskMap.put(TimerTaskCommand.LEVEL_TECH, new LevelUpTechTask(TimerTaskCommand.LEVEL_TECH));
		timerTaskMap.put(TimerTaskCommand.MOD_SOLODIER, new ModFactoryTask(TimerTaskCommand.MOD_SOLODIER));
		timerTaskMap.put(TimerTaskCommand.PRODUCE_EQUIPMENT, new ProduceEquipmentTask(TimerTaskCommand.PRODUCE_EQUIPMENT));
		timerTaskMap.put(TimerTaskCommand.PRODUCE_FRAGMENT, new ProduceFragmentTask(TimerTaskCommand.PRODUCE_FRAGMENT));
		timerTaskMap.put(TimerTaskCommand.REFORM_QUEUE, new ReformTask(TimerTaskCommand.REFORM_QUEUE));
		timerTaskMap.put(TimerTaskCommand.REMOVE_BUILD, new RemoveBuildTask(TimerTaskCommand.REMOVE_BUILD));
		timerTaskMap.put(TimerTaskCommand.RUN_SOLDIER, new RunCampTask(TimerTaskCommand.RUN_SOLDIER));
		timerTaskMap.put(TimerTaskCommand.COLLECT, new CollectTimerTask(TimerTaskCommand.COLLECT));
		timerTaskMap.put(TimerTaskCommand.BUFF_ITEM, new BuffTimerTask(TimerTaskCommand.BUFF_ITEM));
		timerTaskMap.put(TimerTaskCommand.OCCUPY, new OccupyTimerTask(TimerTaskCommand.OCCUPY));
		timerTaskMap.put(TimerTaskCommand.ALLIANCE_BATTLE_TEAM_LEADER, new AllianceBattleTeamLeaderTask(TimerTaskCommand.ALLIANCE_BATTLE_TEAM_LEADER));
	}
	
	public static ITimerTask<?> getTimerTask(TimerTaskCommand timerTaskCommand) {
		if(timerTaskCommand == null) {
			System.out.println("");
		}
		return timerTaskMap.get(timerTaskCommand);
	}
	
	public static ITimerTask<?> getTimerTask(int timerTaskCommand) {
		TimerTaskCommand timerTaskCommand2 = TimerTaskCommand.getTimerTaskCommand(timerTaskCommand);
		return getTimerTask(timerTaskCommand2);
	}
	
}
