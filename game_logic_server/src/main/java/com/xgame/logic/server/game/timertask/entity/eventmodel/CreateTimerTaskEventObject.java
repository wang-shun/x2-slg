package com.xgame.logic.server.game.timertask.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 创建时间队列
 * @author jacky.jiang
 *
 */
public class CreateTimerTaskEventObject extends EventObject {
	
	/**
	 * 加速的队列类型
	 */
	private int timerTaskCommand;
	
	/**
	 * 队列加速参数
	 */
	private String param;

	/**
	 * 持续时间
	 */
	private int period;
	

	public CreateTimerTaskEventObject(Player player, int type, int timerTaskCommand, int  period, String param) {
		super(player, type);
		this.timerTaskCommand = timerTaskCommand;
		this.param = param;
		this.period = period;
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

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
}
