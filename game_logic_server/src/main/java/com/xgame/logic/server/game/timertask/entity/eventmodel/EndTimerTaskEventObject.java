package com.xgame.logic.server.game.timertask.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.GameLogEventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 结束时间队列
 * @author jacky.jiang
 *
 */
public class EndTimerTaskEventObject extends GameLogEventObject{
	
	/**
	 * 加速的队列类型
	 */
	private int timerTaskCommand;
	
	/**
	 * 队列加速参数
	 */
	private String param;

	
	
	public EndTimerTaskEventObject(Player player, int type, int timerTaskCommand, String param) {
		super(player, type);
		this.timerTaskCommand = timerTaskCommand;
		this.param = param;
		this.type = type;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getTimerTaskCommand() {
		return timerTaskCommand;
	}

	public void setTimerTaskCommand(int timerTaskCommand) {
		this.timerTaskCommand = timerTaskCommand;
	}

	@Override
	public String toString() {
		return "EndTimerTaskEventObject [timerTaskCommand=" + timerTaskCommand
				+ ", param=" + param + "]";
	}
	
}
