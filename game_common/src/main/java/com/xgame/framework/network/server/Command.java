package com.xgame.framework.network.server;

public interface Command extends Runnable {
	public boolean isSync();

	public String name();

	public void execute();
}
