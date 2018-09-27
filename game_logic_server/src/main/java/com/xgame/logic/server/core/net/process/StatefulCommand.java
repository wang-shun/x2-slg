package com.xgame.logic.server.core.net.process;




public abstract class StatefulCommand<E> implements Runnable{
	
	protected int callbackId;

	public void run() {
		this.execute();
	}

	public abstract void execute();
	

	public int getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(int callbackId) {
		this.callbackId = callbackId;
	}
}
