package com.log;

import java.util.concurrent.locks.ReentrantLock;

import com.google.common.eventbus.EventBus;
import com.xgame.utils.BlockingHashBaseMap;

/**
 *
 *2016-10-17  21:37:29
 *@author ye.yuan
 *
 */
public class LogSubscribeTable {
	
	/**
	 * 独占锁,用来锁订阅二维表
	 */
	private final ReentrantLock lock = new ReentrantLock();
	
	private BlockingHashBaseMap<Integer, Integer, LogSubscribe> subscribes = new  BlockingHashBaseMap<>();
	
	private EventBus bus = new EventBus();
	
	public EventBus getBus() {
		return bus;
	}

	public void setBus(EventBus bus) {
		this.bus = bus;
	}


	public ReentrantLock getLock() {
		return lock;
	}

	public BlockingHashBaseMap<Integer, Integer, LogSubscribe> getSubscribes() {
		return subscribes;
	}

	public void setSubscribes(
			BlockingHashBaseMap<Integer, Integer, LogSubscribe> subscribes) {
		this.subscribes = subscribes;
	}
	
}
