package com.xgame.logic.server.core.gamelog.event;

public interface IEventObject extends IEvent {
	public Integer getType();

	public Object getObject();

	public Object getArgs();
}
