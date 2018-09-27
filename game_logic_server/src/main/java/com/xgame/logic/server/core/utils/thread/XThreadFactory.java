package com.xgame.logic.server.core.utils.thread;

import java.util.concurrent.ThreadFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池工厂
 * @author jacky.jiang
 *
 */
@Slf4j
public class XThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "xt");
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.error("线程池抛异常:", e);
			}
		});
		return t;
	}
}
