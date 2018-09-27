package com.xgame.framework.rpcpool;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

import org.apache.thrift.async.TAsyncClient;
import org.apache.thrift.async.TAsyncClientFactory;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;

public class ThriftBroker<C extends TAsyncClient> {
	// Holds all of our Async Clients
	private final ConcurrentLinkedQueue<C> instances = new ConcurrentLinkedQueue<C>();
	// Holds all of our postponed requests
	private final ConcurrentLinkedQueue<ThriftRequest<C, ?>> requests = new ConcurrentLinkedQueue<ThriftRequest<C, ?>>();
	// Holds our executor, if any
	private Executor exe = null;

	/**
	 * This factory runs in thread bounce mode, meaning that if you call it from
	 * many threads, execution bounces between calling threads depending on when
	 * execution is needed.
	 */
	public ThriftBroker(final int clients, final int clients_per_message_processing_thread, final String host,
			final int port, ThriftClientFactoryBuilder<C> factoryBuilder) throws IOException {

		// We only need one protocol factory
		TProtocolFactory proto_fac = new TCompactProtocol.Factory();
		// Create our clients
		TAsyncClientFactory<C> fac = null;
		for (int i = 0; i < clients; i++) {

			if (fac == null || i % clients_per_message_processing_thread == 0) {
				fac = factoryBuilder.buildFactory(new TAsyncClientManager(), proto_fac);
			}

			instances.add(fac.getAsyncClient(new TNonblockingSocket(host, port)));
		}
	}

	/**
	 * This factory runs callbacks in whatever mode the executor is setup for,
	 * not on calling threads.
	 */
	public ThriftBroker(Executor exe, final int clients, final int clients_per_message_processing_thread,
			final String host, final int port, ThriftClientFactoryBuilder<C> factoryBuilder) throws IOException {
		this(clients, clients_per_message_processing_thread, host, port, factoryBuilder);
		this.exe = exe;
	}

	// Call this to grab an instance
	public void req(final ThriftRequest<C, ?> req) {
		final C cli;
		synchronized (instances) {
			cli = instances.poll();
		}
		if (cli != null) {
			if (exe != null) {
				// Executor mode
				exe.execute(new Runnable() {

					@Override
					public void run() {
						req.go(ThriftBroker.this, cli);
					}

				});
			} else {
				// Thread bounce mode
				req.go(this, cli);
			}
			return;
		}
		// No clients immediately available
		requests.add(req);
	}

	void ret(final C cli) {
		final ThriftRequest<C, ?> req;
		synchronized (requests) {
			req = requests.poll();
		}
		if (req != null) {
			if (exe != null) {
				// Executor mode
				exe.execute(new Runnable() {

					@Override
					public void run() {
						req.go(ThriftBroker.this, cli);
					}
				});
			} else {
				// Thread bounce mode
				req.go(this, cli);
			}
			return;
		}
		// We did not need this immediately, hold onto it
		instances.add(cli);
	}
}
