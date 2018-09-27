package com.xgame.framework.executer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
 
public class ParallelExecutor {

	private ExecutorService[] services;
	private long workThread = 0;
	boolean start;
	static AtomicInteger index = new AtomicInteger(0);
	
	public ParallelExecutor( ) {
		
	}

	public ParallelExecutor(final String name, final int workThread) {
		services = new ExecutorService[workThread];
		this.workThread = workThread;
		for (int i = 0; i < workThread; i++) {

			services[i] = Executors.newSingleThreadExecutor(new ThreadFactory() {
						@Override
						public Thread newThread(Runnable r) {
							log.debug("{}", index);
							return new Thread(r, name + "-"
									+ index.incrementAndGet());
						}
					});
		}
		start = true;
	}

	public void execute(long id, Runnable command) {  
		services[(int) (id % workThread)].execute(command);
	}
	
	public ExecutorService getPlayerExecutor(long id) {
		return services[(int) (id % workThread)];
	}

	public synchronized void shutdown() {
		if (!start) {
			return;
		}
		
		start = false;
		for (int i = 0; i < workThread; i++) {
			services[i].shutdownNow();
		}
	}
}
