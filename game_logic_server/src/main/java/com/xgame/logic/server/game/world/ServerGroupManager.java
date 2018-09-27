package com.xgame.logic.server.game.world;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.system.GlobalManager;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.world.entity.ServerGroup;
import com.xgame.utils.TimeUtils;

/**
 * 服务器组管理器
 * @author jacky.jiang
 *
 */
@Component
public class ServerGroupManager {
	
	@Autowired
	private GlobalManager globalManager;
	@Autowired
	private GlobalRedisClient GlobalRedisClient;
	
	@Startup(order = StartupOrder.SERVERGROUP_START, desc= "服务器组信息启动")
	public void init() {
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		if(globalEnity != null && globalEnity.getServerOpenTime() <= 0) {
			synchronized (globalEnity) {
				globalEnity.setServerOpenTime(TimeUtils.getCurrentTimeMillis());
				globalManager.saveGlobalEntity(globalEnity);
				//TODO 需要策划定义一下 到底什么算开服时间
				ServerGroup serverGroup = new ServerGroup();
				serverGroup.setGateInfo(InjectorUtil.getInjector().gateInfo);
				serverGroup.setServerOpenTime(TimeUtils.getCurrentTimeMillis());
				serverGroup.setOnlineNum(0);
				serverGroup.setThronePic("");
				serverGroup.setServerId(InjectorUtil.getInjector().serverId);
				
				saveServeGroup(serverGroup);
			}
		}
	}
	
	public ServerGroup getServerGroup(int serverId) {
		String groupStr = GlobalRedisClient.hget(ServerGroup.class.getSimpleName(), serverId);
		JBaseData baseData = JsonUtil.fromJSON(groupStr, JBaseData.class);
		if(baseData != null) { 
			ServerGroup serverGroup = new ServerGroup();
			serverGroup.fromJBaseData(baseData);
			return serverGroup;
		}
		return null;
	}
	
	public void saveServeGroup(ServerGroup serverGroup) {
		String groupStr = JsonUtil.toJSON(serverGroup.toJBaseData());
		GlobalRedisClient.hset(ServerGroup.class.getSimpleName(), serverGroup.getServerId(), groupStr);
	}
	
	public List<ServerGroup> getServerGroups() {
		List<ServerGroup> serverGroups = GlobalRedisClient.hvals(ServerGroup.class);
		return  serverGroups;
	}
}
