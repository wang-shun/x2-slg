package com.xgame.logic.server.game.player.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 加速时间
 * @author jacky.jiang
 *
 */
public class SpeedUpEventObject extends GameLogEventObject {

	/**
	 * 加速的队列类型
	 */
	private TimerTaskCommand timerTaskCommand;
	
	/**
	 * 队列加速参数
	 */
	private TimerTaskData timerTaskData;
	
	public SpeedUpEventObject(Player player, int type, TimerTaskCommand timerTaskCommand, TimerTaskData timerTaskData) {
		super(player, type);
		this.timerTaskCommand = timerTaskCommand;
		this.timerTaskData = timerTaskData;
		this.type = type;
		this.player = player;
	}
	
	public TimerTaskCommand getTimerTaskCommand() {
		return timerTaskCommand;
	}

	public void setTimerTaskCommand(TimerTaskCommand timerTaskCommand) {
		this.timerTaskCommand = timerTaskCommand;
	}
	
	public TimerTaskData getTimerTaskData() {
		return timerTaskData;
	}

	public void setTimerTaskData(TimerTaskData timerTaskData) {
		this.timerTaskData = timerTaskData;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
