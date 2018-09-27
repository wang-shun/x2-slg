package com.xgame.framework.network.server;

public interface CommandState<T, D> {
	public T getContext();

	public void setContext(T context);

	public D getData();

	public void setData(D data);

}
