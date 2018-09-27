package com.xgame.framework.rpcpool;
import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncClientFactory;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TProtocolFactory;

public interface ThriftClientFactoryBuilder<T extends TAsyncClient> {
	TAsyncClientFactory<T> buildFactory(TAsyncClientManager tAsyncClientManager, TProtocolFactory proto_fac);
}
