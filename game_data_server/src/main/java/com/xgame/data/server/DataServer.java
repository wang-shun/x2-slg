package com.xgame.data.server;

import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.xgame.framework.rpc.DataServerService;


@Slf4j
@Component
public class DataServer {

	@Value("${xgame.data.server.port}")
	private int rpcPort;

	@Autowired
	private DataServerServiceImpl serverImpl;

	public void startRpcServer() {
		try {
			log.debug("#################### dataServer blocking start at:{}", rpcPort);

			TProcessor tprocessor = new DataServerService.Processor<DataServerService.Iface>(
					serverImpl);
			TServerSocket tnbSocketTransport = new TServerSocket(rpcPort);
			TThreadPoolServer.Args tnbArgs = new TThreadPoolServer.Args(
					tnbSocketTransport);
			tnbArgs.processor(tprocessor);
			tnbArgs.protocolFactory(new TBinaryProtocol.Factory());

			// 使用阻塞式IO 
			TServer server = new TThreadPoolServer(tnbArgs);
			server.serve();
			
			
		} catch (Exception e) {
			log.debug("DBServer:{} start error!!!", rpcPort);
			e.printStackTrace();
		}
	}
}
