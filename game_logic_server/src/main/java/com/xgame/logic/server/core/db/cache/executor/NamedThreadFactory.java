package com.xgame.logic.server.core.db.cache.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可命名线程组
 * @author jacky
 */
public class NamedThreadFactory implements ThreadFactory {

	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	public NamedThreadFactory(ThreadGroup group, String name) {
		this.group = group;
		namePrefix = group.getName() + ":" + name;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		t.setDaemon(true);
		return t;
	}

}
