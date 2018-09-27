package com.xgame.logic.server.game.timertask.entity.system;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.utils.EnumUtils;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;

/**
 * 系统任务
 * @author jacky.jiang
 *
 */
public class SystemTimerTaskHolder {

	private static Map<SystemTimerCommand, ISystemTask<?>> systemTimerMap = new ConcurrentHashMap<>();
	
	static {
		systemTimerMap.put(SystemTimerCommand.SHOP, new ShopTimerTask(SystemTimerCommand.SHOP));
		systemTimerMap.put(SystemTimerCommand.GAME_EVENT, new GameEventTimerTask(SystemTimerCommand.GAME_EVENT));
		systemTimerMap.put(SystemTimerCommand.ALLIANCE_BUILD, new AllianceBuildTimerTask(SystemTimerCommand.ALLIANCE_BUILD));
	}
	
	public static ISystemTask<?> getTimerTask(SystemTimerCommand command) {
		return systemTimerMap.get(command);
	}
	
	public static ISystemTask<?> getTimerTask(int timerTaskCommand) {
		SystemTimerCommand systemTimerCommand = EnumUtils.getEnum(SystemTimerCommand.class, timerTaskCommand);
		return getTimerTask(systemTimerCommand);
	}
	
}
