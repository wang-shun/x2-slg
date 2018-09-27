package com.xgame.framework.executer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.stereotype.Component;


/**
 * 世界地图线程
 * @author jacky.jiang
 *
 */
 
public class MapExecutor {
	
	private ExecutorService service;
	
	public MapExecutor( ) {
		
	}

	public MapExecutor(final String name) {
		service = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, name);
			}
		});
	}

	public void execute(Runnable command) {
		service.execute(command);
	}

	public synchronized void shutdown() {
		if (service.isTerminated()) {
			return;
		}
		service.shutdownNow();
	}

	public ExecutorService getService() {
		return service;
	}

	public void setService(ExecutorService service) {
		this.service = service;
	}
}
