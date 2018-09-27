package com.xgame.gate.server;

import lombok.extern.slf4j.Slf4j;

import com.xgame.data.service.IService;
import com.xgame.gate.server.config.ConfigManager;
import com.xgame.gate.server.constant.ServerType;
import com.xgame.gate.server.core.GateServer;
import com.xgame.gate.server.core.GateToClientCodecFactory;
import com.xgame.gate.server.core.GateToServerCodecFactory;

/**
 * 网关管理器
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateAgentServer implements IService {
	
	
	private volatile static GateAgentServer gateAgentServer;
	
	public synchronized static GateAgentServer getInstance() {
		if(gateAgentServer == null) {
			gateAgentServer = new GateAgentServer();
		}
		return gateAgentServer;
	}
	

	// 服务器-网关
	private IService s2gServer;
	// 客户端-网关
	private  IService c2gServer;

    @Override
    public void init() {
    	int c2gport = ConfigManager.getInstance().getConfig().getInt("realm.c2gport");
    	int s2gport = ConfigManager.getInstance().getConfig().getInt("realm.s2gport");
    	IService s2gServer = new GateServer(s2gport, ServerType.S2G, new GateToServerCodecFactory());
    	IService c2gServer = new GateServer(c2gport, ServerType.C2G, new GateToClientCodecFactory());
        s2gServer.init();
        c2gServer.init();
        log.info("网关启动成功，clientToGatePort:{}, serverToGatePort:{}", c2gport, s2gport);
    }

    @Override
    public void shutdown() {
    	s2gServer.shutdown();
    	c2gServer.shutdown();
    }
}
