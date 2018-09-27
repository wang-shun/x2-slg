package com.xgame.logic.server.core.gamelog.event;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 事件监听抽象类
 * @author jacky.jiang
 *
 */
public abstract class AbstractEventListener implements IListener {
	
	@Autowired
	private EventBus eventBus;
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}

	public EventBus getEventBus() {
		return eventBus;
	}

}
