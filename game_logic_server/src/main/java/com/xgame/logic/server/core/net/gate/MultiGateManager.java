package com.xgame.logic.server.core.net.gate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 网关管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class MultiGateManager {
	
	@Value("${xgame.gate.server}")
	private String gateInfo;
	@Autowired
	private ObjectFactory<GateMessageHandler> factory;
	
	// gateServer 缓存
	private Map<Integer, GateServer> gateMap = new ConcurrentHashMap<>();
	
	/**
	 * 启动初始化gate
	 */
	public void init() {
		String[] gates = gateInfo.split(";");
		for(String gate : gates)  {
			String[] gateInfo = gate.split(":");
			GateServer gateServer = new GateServer(factory);
			int gateId = Integer.valueOf(gateInfo[0]);
			
			// 启动gateServer连接
			gateServer.start(gateInfo[1], Integer.valueOf(gateInfo[2]), gateId, 8);
			gateMap.put(gateId, gateServer);
			log.error(String.format("server connnect gate success。。。。gateId:{%s}, host:{%s}, port:{%s}", gateId, gateInfo[1], Integer.valueOf(gateInfo[2])));
		}
	}
	
	/**
	 * 获取server服务器
	 * @param gateId
	 * @return
	 */
	public GateServer gateServer(int gateId) {
		return gateMap.get(gateId);
	}
	
	/**
	 * 返回网关列表
	 * @return
	 */
	public List<GateServer> getServerList() {
		return Lists.newArrayList(gateMap.values());
	}
	
	/**
	 * 关闭服务器
	 */
	public void shutdown() {
		for(GateServer gateServer : gateMap.values()) {
			gateServer.shutDown();
		}
	}
	
}
