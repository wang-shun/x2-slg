package com.xgame.logic.server.core.gamelog.event;

import com.xgame.logic.server.game.player.entity.Player;

/**
 * 事件基类
 * @author jacky.jiang
 *
 */
public class EventObject implements IEventObject {
	
	protected transient Player player;

	protected Integer type;

	protected Object obj;

	protected Object args;

	public EventObject(Player player, Integer type, Object obj) {
		super();
		this.player = player;
		this.type = type;
		this.obj = obj;
	}

	public EventObject(Player player, Integer type) {
		super();
		this.player = player;
		this.type = type;
		this.obj = null;
	}
	
	public EventObject(Integer type) {
		this.type = type;
	}

	public void setArgs(Object args) {
		this.args = args;
	}

	public Object getArgs() {
		return args;
	}

	public Object getObject() {
		return obj;
	}

	public Integer getType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}
}
