package com.xgame.framework.rpcpool;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClient;

// This is the request
public abstract class ThriftRequest<T extends TAsyncClient, R> implements AsyncMethodCallback<R>{

	private ThriftBroker<T> broker;
	private T client;
	
	void go(ThriftBroker<T> broker, final T client) {
		this.broker = broker;
		this.client = client;
		this.request(client);
	}

	public abstract void request(T client);
	
	public abstract void response(R response);

	@Override
	public void onComplete(R response) {
		broker.ret(client);
		response(response);
	}

}
