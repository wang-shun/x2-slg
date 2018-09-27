package com.xgame.framework.rpc;

import java.nio.ByteBuffer;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xgame.framework.rpc.DataServerService.Client;

@Slf4j
@Component
public class DataRpcCall {
	@Value("${xgame.data.server.host}")
	private String ip;

	@Value("${xgame.data.server.port}")
	private int port;

	private TSocket transport;

	private TProtocol tprotocol;

//	private DataServerService.Client client;

	private static final int TIMEOUT = 60 * 1000;// Milliseconds

	@PostConstruct
	private void init() {

		connect();
	}

	void connect() {
		try {
			transport = new TSocket(ip, port, TIMEOUT);
			tprotocol = new TBinaryProtocol(transport);
//			client = new DataServerService.Client(tprotocol);
			transport.open();

		} catch (Exception e1) {
			log.error("DataServer connect db error db ip={}  port={}", ip, port);
			e1.printStackTrace();
		}
	}

	void reConnect() {
		transport.close();
		connect();
	}

	public boolean isAvailable() {
		try {
			Client client = getClient();
			if(!client.isAvailable()) {
				client = new DataServerService.Client(tprotocol);
			}
			
			boolean rs = client.isAvailable();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("call DataServer error", e);
		} 
		
		return false;
	}

	public void saveBatch(List<String> key, List<ByteBuffer> datas) {
		try {
			Client client = getClient();
			client.saveBatch(key, datas);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("call DataServer error", e);
		}
	}

	public long incr(String key) {
		try {
			Client client = getClient();
			long rs = client.incr(key);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("call DataServer error", e);
		}
		return -1;
	}

	public long incrBy(String key, long value) {
		try {
			Client client = getClient();
			long rs = client.incrBy(key, value);

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("call DataServer error", e);
		}
		return -1;
	}

	public void save(String key, ByteBuffer data) {
		try {
			Client client = getClient();
			client.save(key, data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("call DataServer error");
		}
	}
	
	public DataServerService.Client getClient() {
		Client client = new DataServerService.Client(tprotocol);
		return client;
	}
	
}
